package edu.ecnu.ykyl.web.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
import edu.ecnu.ykyl.replyentity.ReplyPKUser;
import edu.ecnu.ykyl.service.QuestionService;
import edu.ecnu.ykyl.service.UserService;
import edu.ecnu.ykyl.web.websocket.entity.PKRoom;
import edu.ecnu.ykyl.web.websocket.service.PKRoomService;

public class SystemWebSocketHandler implements WebSocketHandler {

	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private PKRoomService pkRoomService;

	private PKRoom pkRoom;
	private static Integer pkRoomCount;
	private Map<String, Object> questions;

	// 用户id，表示当websocket服务开启时，该用户的编号
	private static Integer userId;
	// pkRoomId,表示当websocket服务开启时，该pkRoom的编号
	private static Long pkRoomId;

	// 第一个node的websocketId
	private WebSocketSession firstWebSocketSession;
	// 第一个node的用户信息
	ReplyPKUser firstUser = null;
	Map<String, Object> firstUserResultMap = null;

	// 第二个node的websocketId
	private WebSocketSession secondWebSocketSession;
	// 第二个node的用户信息
	ReplyPKUser secondUser = null;
	Map<String, Object> secondUserResultMap = null;

	private static final ArrayList<WebSocketSession> users;

	// 存放websocketSession与User的对应关系
	private static ConcurrentLinkedQueue<Map<WebSocketSession, ReplyPKUser>> matchQueue;
	// 存放聊天室与题目的关系
	private static ConcurrentLinkedQueue<PKRoom> pKRoomQueue;

	static {
		users = new ArrayList<>();
		matchQueue = new ConcurrentLinkedQueue<Map<WebSocketSession, ReplyPKUser>>();
		pKRoomQueue = new ConcurrentLinkedQueue<PKRoom>();
		pkRoomCount = 0;
		userId = 0;
		pkRoomId = 0L;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {

		ReplyPKUser replyPKUser = new ReplyPKUser();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		replyPKUser = (ReplyPKUser) session.getAttributes().get(
				Constants.SESSION_USER);
		String username = (String) replyPKUser.getName();
		System.out
				.println(username + " connect to the websocket success......");

		if (username != null) {
			
			//向前端发送测试消息
			/*sendMessageToAllUsers(new TextMessage(username+" join!!!"));
			session.sendMessage(new TextMessage(JSONObject.fromObject(resultMap).toString()));
			session.sendMessage(new TextMessage("当前在线人数： "+users.size()));*/
			
			++userId;
			users.add(session);
			resultMap.put("user", replyPKUser);
			if (matchQueue.size() >= 1) {
				if (pkRoomService.findMaxPKRoomId() != null) {
					pkRoomId = pkRoomService.findMaxPKRoomId() + 1;
				} else {
					pkRoomId = 1L;
				}
				WebSocketSession matchWebSocketSession = null;
				Map<WebSocketSession, ReplyPKUser> matcher = matchQueue.poll();
				Set<WebSocketSession> keySet = matcher.keySet();
				Iterator<WebSocketSession> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					matchWebSocketSession = iterator.next();
				}
				/**
				 * 互发对手的身份信息
				 */
				firstWebSocketSession = matchWebSocketSession;
				secondWebSocketSession = session;

				firstUser = (ReplyPKUser) matchWebSocketSession.getAttributes()
						.get(Constants.SESSION_USER);
				secondUser = (ReplyPKUser) secondWebSocketSession
						.getAttributes().get(Constants.SESSION_USER);
				firstUserResultMap = new HashMap<String, Object>();
				secondUserResultMap = new HashMap<String, Object>();
				firstUserResultMap.put("oppo", firstUser);
				secondUserResultMap.put("oppo", secondUser);
				// 将匹配成功的对象赋予PKRoomId，以便之后发送题目
				firstUserResultMap.put("pkRoomId", pkRoomId);
				secondUserResultMap.put("pkRoomId", pkRoomId);
				firstUserResultMap.put("sender", "hello");
				secondUserResultMap.put("sender", "hello");
				firstWebSocketSession.sendMessage(new TextMessage(JSONObject
						.fromObject(secondUserResultMap).toString()));
				secondWebSocketSession.sendMessage(new TextMessage(JSONObject
						.fromObject(firstUserResultMap).toString()));

				pkRoomCount++;
				pkRoom = new PKRoom();
				pkRoom.setId(pkRoomId);
				// 随机获取一套题目
				questions = questionService.replyPKQuestions(2, 5);
				// 随机获取一套题目的顺序

				pkRoom.setQuestions(JSONObject.fromObject(questions).toString());
				WebSocketSession[] wsSession = { firstWebSocketSession,
						secondWebSocketSession };
				pkRoom.setWebSocketSession(wsSession);
				pKRoomQueue.add(pkRoom);

				// 将pkRoom持久化
				pkRoomService.insertPKRoom(pkRoom.getQuestions(),
						pkRoom.getId());

			} else {
				Map<WebSocketSession, ReplyPKUser> map = new HashMap<WebSocketSession, ReplyPKUser>();
				map.put(session, replyPKUser);
				matchQueue.add(map);
			}
		}
	}

	@Override
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		//向前端发送测试消息
		/*sendMessageToAllUsers(message);*/

		// 获取session中的pkinfo
		ReplyPKUser replyPKUser = new ReplyPKUser();
		replyPKUser = (ReplyPKUser) session.getAttributes().get(
				Constants.SESSION_USER);
		String pkRoomId = "";
		String myMessage = (String) message.getPayload();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		String pkResult = pkRoomService.getPKResultByPKRoomId(1L);
		pkResult = "[" + pkResult + "]";
		Map<String, Object> pkResultMap = PKResultHandler(pkResult);
		int mapSize = pkResultMap.size();

		if (myMessage.startsWith("交卷") && mapSize > 1) {
			Map<String, Object> pkInfoMap = new HashMap<String, Object>();
			// 封装userInfo
			String pkInfo = replyPKUser.getPKInfo();
			System.out.println(pkInfo);
			pkInfoMap = convertPKInfo2Map(pkInfo);
			pkResultMap.put("userinfo", pkInfoMap);
			// 判断输赢
			Map<String, Object> userresultMap = (Map<String, Object>) pkResultMap
					.get("userresult");
			Map<String, Object> opporesultMap = (Map<String, Object>) pkResultMap
					.get("opporesult");
			int userSuccessCount = Integer.parseInt(userresultMap
					.get("success").toString());
			int oppoSuccessCount = Integer.parseInt(opporesultMap
					.get("success").toString());
			if (userSuccessCount >= oppoSuccessCount) {
				pkResultMap.put("result", "success");
			} else {
				pkResultMap.put("result", "fail");
			}
			pkResultMap.put("sender", "submit");
			sendMessageToAllUsers(new TextMessage(JSONObject.fromObject(
					pkResultMap).toString()));
		}

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		System.out.println("websocket connection closed......");
		users.remove(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
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
		// String str="success: 22,fail: 21,info: 还差30场晋级Lv2";
		String[] splits = str.split(",");
		for (int i = 0; i < splits.length; i++) {
			String[] kv = splits[i].split(":");
			resultMap.put(kv[0], kv[1]);
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
