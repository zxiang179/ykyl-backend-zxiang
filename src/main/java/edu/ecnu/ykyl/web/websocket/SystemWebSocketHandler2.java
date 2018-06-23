package edu.ecnu.ykyl.web.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.model.UserRepository;
import edu.ecnu.ykyl.replyentity.ReplyPKUser;
import edu.ecnu.ykyl.service.QuestionService;
import edu.ecnu.ykyl.service.UserService;
import edu.ecnu.ykyl.web.websocket.entity.PKRoom;
import edu.ecnu.ykyl.web.websocket.service.PKRoomService;

public class SystemWebSocketHandler2 implements WebSocketHandler {

	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private PKRoomService pkRoomService;

	// pkRoomId,表示当websocket服务开启时，该pkRoom的编号
	private static Long pkRoomId;

	private static final ArrayList<WebSocketSession> users;
	//保存所有成功建立连接的ws对象
	private static final ArrayList<WebSocketSession> wsusers;
	//存放wssession和userInfo的对应关系
	private static final Map<WebSocketSession,User> wsUserInfo;
	//存放wssession和pkId的对应关系
	private static final Map<WebSocketSession,Long> wsUserPKRoomID;
	//等待匹配的队列
	private static final ConcurrentLinkedQueue<WebSocketSession> waitQueue;
	//匹配成功的队列
	private static final Map<Long,PKRoom> matchedQueue;

	static {
		pkRoomId = 0L;
		users = new ArrayList<>();
		wsusers=new ArrayList<>();
		waitQueue=new ConcurrentLinkedQueue<WebSocketSession>();
		matchedQueue=new HashMap<Long,PKRoom>();
		wsUserInfo=new HashMap<WebSocketSession,User>();
		wsUserPKRoomID=new HashMap<WebSocketSession,Long>();
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
        
		User user = (User)session.getAttributes().get(Constants.LOGIN_USER);
		//将用户信息存放在wsUserInfo中
		wsUserInfo.put(session, user);
		if(user!=null){
			users.add(session);
			wsUserInfo.put(session, user);
			//判断当前的等待队列中的人数，如果大于等于2则可以匹配成功,否则将当前session添加到等待队列
			if(waitQueue.size()>=1){
				synchronized (WebSocketHandler.class) {
					//获取当前的pkRoomId
					if (pkRoomService.findMaxPKRoomId() != null) {
						pkRoomId = pkRoomService.findMaxPKRoomId() + 1;
					} else {
						pkRoomId = 1L;
					}
					WebSocketSession matchWebSocketSession = null;
					//从等待队列中移除wssession
					matchWebSocketSession=waitQueue.poll();
					//获取到两个匹配成功的wsSession
					User user1=(User)matchWebSocketSession.getAttributes().get(Constants.LOGIN_USER);
					User user2=(User)session.getAttributes().get(Constants.LOGIN_USER);
					ReplyPKUser replyPKUser1 = userToPkUser(user1);
					ReplyPKUser replyPKUser2 = userToPkUser(user2);
					//互发对手信息
					sendMessage2Oppo(matchWebSocketSession,replyPKUser1,session,replyPKUser2);
					//创建pkRoom对象，并保存在匹配成功队列，并将pkRoom持久化在数据库
					createAndSavePKRoom(pkRoomId,matchWebSocketSession,session);
					
				}
			}else{
				waitQueue.add(session);
			}
		}
		
	}

	@Override
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		//根据房间号从数据库中获取pk的结果
		//当resultMap的值大于2则准备发送
		
		Long pkRoomIdTmp = wsUserPKRoomID.get(session);
		String myMessage = (String) message.getPayload();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		String pkResult = pkRoomService.getPKResultByPKRoomId(pkRoomIdTmp);
		pkResult = "[" + pkResult + "]";
		Map<String, Object> pkResultMap = PKResultHandler(pkResult);
		int mapSize = pkResultMap.size();

		if (myMessage.startsWith("交卷") && mapSize > 1) {
			WebSocketSession wssession1 = null;
			Map<String,Object> pkResultMap1 = new HashMap<String,Object>();
			Map<String, Object> pkInfoMap1 = new HashMap<String, Object>();
			Map<String, Object> pkInfoMap2 = new HashMap<String, Object>();
			// 封装userInfo2
			User user2 = wsUserInfo.get(session);
			String user2PKHist = userService.findPKHistStr(user2.getId());
			if(!"".equals(user2PKHist)){
				user2.setPKRecord(userService.findPKHistStr(user2.getId()));	
			}
			
			ReplyPKUser replyPKUser2 = userToPkUser(user2);
			String pkInfo2 = replyPKUser2.getPKInfo();
			pkInfoMap2 = convertPKInfo2Map(pkInfo2);
			pkResultMap.put("userinfo", pkInfoMap2);
			//封装userInfo1
			PKRoom pkRoom = matchedQueue.get(pkRoomIdTmp);
			WebSocketSession[] wsSessionPair = pkRoom.getWebSocketSession();
			if(wsSessionPair[0]!=session){
				wssession1=wsSessionPair[0];
			}else{
				wssession1=wsSessionPair[1];
			}
			User user1 = wsUserInfo.get(wssession1);
			String user1PKHist = userService.findPKHistStr(user1.getId());
			if(!"".equals(user1PKHist)){
				user1.setPKRecord(userService.findPKHistStr(user1.getId()));	
			}
			
			ReplyPKUser replyPKUser1 = userToPkUser(user1);
			String pkInfo1 = replyPKUser2.getPKInfo();
			pkInfoMap1 = convertPKInfo2Map(pkInfo1);
			pkResultMap1.put("userinfo", pkInfoMap1);
			
			// 判断输赢
			Map<String, Object> userresultMap = (Map<String, Object>) pkResultMap
					.get("userresult");
			Map<String, Object> opporesultMap = (Map<String, Object>) pkResultMap
					.get("opporesult");
			int userSuccessCount = Integer.parseInt(userresultMap
					.get("success").toString());
			int oppoSuccessCount = Integer.parseInt(opporesultMap
					.get("success").toString());
			if (userSuccessCount > oppoSuccessCount) {
				pkResultMap.put("result", "success");
				pkResultMap1.put("result", "fail");
			} else if(userSuccessCount < oppoSuccessCount){
				pkResultMap.put("result", "fail");
				pkResultMap1.put("result", "success");
			}else{
				pkResultMap.put("result", "draw");
				pkResultMap1.put("result", "draw");
			}
			pkResultMap.put("sender", "submit");
			pkResultMap1.put("sender", "submit");
			
			//将PK的结果存入历史记录
			long user1ID = user1.getId();
			long user2ID = user2.getId();
			double user1PKRate = 0.0;
			double user2PKRate = 0.0;
			Map<String, String> user1PKHistMap = userService.findPKHist(user1ID);
			Map<String, String> user2PKHistMap = userService.findPKHist(user2ID);
			if(pkResultMap1.get("result").equals("success")){
				//user1 success,user2 fail
				Integer user1SuccessCount = Integer.valueOf(user1PKHistMap.get("success"));
				user1SuccessCount++;
				user1PKHistMap.put("success", user1SuccessCount.toString());
				Integer user1FailCount = Integer.valueOf(user1PKHistMap.get("fail"));
				user1PKRate=user1SuccessCount/(user1SuccessCount+user1FailCount);
				
				Integer user2FailCount = Integer.valueOf(user2PKHistMap.get("fail"));
				user2FailCount++;
				user2PKHistMap.put("fail", user2FailCount.toString());
				Integer user2SuccessCount = Integer.valueOf(user2PKHistMap.get("success"));
				user2PKRate=user2SuccessCount/(user2SuccessCount+user2FailCount);
				
			}else if(pkResultMap.get("result").equals("success")){
				//user2 success,user1 fail
				Integer user2SuccessCount = Integer.valueOf(user2PKHistMap.get("success"));
				user2SuccessCount++;
				user2PKHistMap.put("success", user2SuccessCount.toString());
				Integer user2FailCount = Integer.valueOf(user2PKHistMap.get("fail"));
				user2PKRate=user2SuccessCount/(user2SuccessCount+user2FailCount);
				
				Integer user1FailCount = Integer.valueOf(user1PKHistMap.get("fail"));
				user1FailCount++;
				user1PKHistMap.put("fail", user1FailCount.toString());
				Integer user1SuccessCount = Integer.valueOf(user1PKHistMap.get("success"));
				user1PKRate=user1SuccessCount/(user1SuccessCount+user1FailCount);
				
			}
			userService.updatePKHist(user1ID,user1PKRate,user1PKHistMap);
			userService.updatePKHist(user2ID,user2PKRate,user2PKHistMap);
			
			//封装user1的pkResultMap1
			pkResultMap1.put("opporesult", pkResultMap.get("userresult"));
			pkResultMap1.put("userresult", pkResultMap.get("opporesult"));
			
			wssession1.sendMessage(new TextMessage(JSONObject.fromObject(
					pkResultMap1).toString()));
			session.sendMessage(new TextMessage(JSONObject.fromObject(
					pkResultMap).toString()));
			
			System.out.println("pkResultMap1======"+JSONObject.fromObject(pkResultMap1).toString());
			System.out.println("pkResultMap======="+JSONObject.fromObject(pkResultMap).toString());
			
			//关闭处理
			removeSession(session);
			removeSession(wssession1);
			//删除pkRoom中的pkResult
			pkRoomService.removePKRoomByPKRoomId(pkRoomIdTmp);
			
		}

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		System.out.println("websocket connection closed......");
		if(users.contains(session)){
			users.remove(session);
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		System.out.println("websocket connection  error and closed......");
		users.remove(session);
	}

	public Map<String, Object> convertPKInfo2Map(String str) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(str.length()==0||str=="0"||str==null){
			resultMap.put("success", "0");
			resultMap.put("fail", "0");
			resultMap.put("info", "还差30场晋级Lv1");
		}else{
			// String str="success: 22,fail: 21,info: 还差30场晋级Lv2";
			String[] splits = str.split(",");
			for (int i = 0; i < splits.length; i++) {
				String[] kv = splits[i].split(":");
				resultMap.put(kv[0], kv[1]);
			}
		}
		return resultMap;
	}

	/**
	 * 将从数据库中取出的pk信息取出，封装为map的格式
	 * 
	 * @param pkResult
	 * 格式：{"fail":8,"success":3,"name":"张三","time":30000},{"fail":3,"success":0,"name":"haha","time":30000}
	 * @return
	 */
	public Map<String, Object> PKResultHandler(String pkResult) {
		// {"fail":8,"success":3,"name":"张三","time":30000},{"fail":3,"success":0,"name":"haha","time":30000}
		// pkResult="[{'fail':8,'success':3,'name':'张三','time':30000},{'fail':3,'success':0,'name':'haha','time':30000}]";
		JSONArray jsonArray = JSONArray.fromObject(pkResult);
		int size = jsonArray.size();
		String fail = "";
		String success = "";
		String name = "";
		String time = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> tempMap = null;
		for (int i = 0; i < size; i++) {
			tempMap = new HashMap<String, Object>();
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			tempMap.put("fail", jsonObject.get("fail").toString());
			tempMap.put("success", jsonObject.get("success").toString());
			tempMap.put("name", (String) jsonObject.get("name"));
			tempMap.put("time", jsonObject.get("time").toString());
			// 执行这一步的wsSession必定为第二个session
			if (i == 0) {
				resultMap.put("opporesult", tempMap);
			} else {
				resultMap.put("userresult", tempMap);
			}
		}
		return resultMap;
		
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	/**
	 * 封装pkRoom，并将pkRoom以pkRoomId-PkRoom的形式存放在匹配成功的队列
	 */
	public void createAndSavePKRoom(Long pkRoomId,WebSocketSession session1,WebSocketSession session2){
		PKRoom pkRoom = new PKRoom();
		Map<String, Object> questions = new HashMap<String,Object>();
		pkRoom.setId(pkRoomId);
		//存储PKRoomId和WSSession的对应关系
		wsUserPKRoomID.put(session1, pkRoomId);
		wsUserPKRoomID.put(session2, pkRoomId);
		// 随机获取一套题目
		questions = questionService.replyPKQuestions(2, 5);
		// 随机获取一套题目的顺序
		pkRoom.setQuestions(JSONObject.fromObject(questions).toString());
		WebSocketSession[] wsSession = { session1,session2 };
		pkRoom.setWebSocketSession(wsSession);
		matchedQueue.put(pkRoomId,pkRoom);
		// 将pkRoom持久化
		pkRoomService.insertPKRoom(pkRoom.getQuestions(),pkRoom.getId());
		//将pkRoomId持久化
		userService.updatePKRoomIdByPhone(pkRoomId, wsUserInfo.get(session1).getPhone());
		userService.updatePKRoomIdByPhone(pkRoomId, wsUserInfo.get(session2).getPhone());
	}
	
	/**
	 * 封装互发对手信息
	 */
	public void sendMessage2Oppo(WebSocketSession session1,ReplyPKUser pkUser1,WebSocketSession session2,ReplyPKUser pkUser2){
		try {
			Map<String,Object> firstUserResultMap = new HashMap<String, Object>();
			Map<String,Object> secondUserResultMap = new HashMap<String, Object>();
			firstUserResultMap.put("oppo", pkUser1);
			secondUserResultMap.put("oppo", pkUser2);
			// 将匹配成功的对象赋予PKRoomId，以便之后发送题目
			firstUserResultMap.put("pkRoomId", pkRoomId);
			secondUserResultMap.put("pkRoomId", pkRoomId);
			firstUserResultMap.put("sender", "hello");
			secondUserResultMap.put("sender", "hello");
			session1.sendMessage(new TextMessage(JSONObject
					.fromObject(secondUserResultMap).toString()));
			session2.sendMessage(new TextMessage(JSONObject
					.fromObject(firstUserResultMap).toString()));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("互发消息失败");
		}
	}
	
	/**
	 * 实现用户到PKUser的转换
	 * @param user
	 * @return
	 */
	public ReplyPKUser userToPkUser(User user) {
		ReplyPKUser pkUser = new ReplyPKUser();
		pkUser.setName(user.getName());
		pkUser.setGrade(user.getGrade());
		pkUser.setSchool(user.getSchool());
		pkUser.setAvatar(user.getAvatar());
		if(user.getPKRate()!=null){
			pkUser.setPkRate(user.getPKRate());	
		}else{
			pkUser.setPkRate(0d);
		}
		if(user.getPKRecord()!=null){
			pkUser.setPKInfo(user.getPKRecord());	
		}else{
			pkUser.setPKInfo("0");
		}
		
		return pkUser;
	}
	
	public void removeSession(WebSocketSession session){
		users.remove(session);
		wsusers.remove(session);
		matchedQueue.remove(wsUserPKRoomID.get(session));
		wsUserInfo.remove(session);
		wsUserPKRoomID.remove(session);
	}
	
	/**
	 * 将信息发送给所有的用户
	 * 
	 * @param message
	 */
	public void sendMessageToAllUsers(WebSocketMessage<?> message) {
		for (WebSocketSession user : users) {
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

