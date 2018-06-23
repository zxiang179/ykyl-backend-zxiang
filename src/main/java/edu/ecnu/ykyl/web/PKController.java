
package edu.ecnu.ykyl.web;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.replyentity.ReplyPKUser;
import edu.ecnu.ykyl.service.UserService;
import edu.ecnu.ykyl.web.websocket.service.PKRoomService;

@RestController
@RequestMapping("/api/PK")
public class PKController {

	@Autowired
	private PKRoomService pkRoomService;
	@Autowired
	private UserService userService;

	/**
	 * 统计用户的PK结果
	 * @return
	 */
	/*public String recordPKResult(){
		
	}*/
	
	/**
	 * 用户每回答完一题，就将答题批改后的数据存入数据库
	 * http://localhost:8888/api/PK/submitResult?pkRoomId=1
	 * @param request
	 * @param pkRoomId
	 * @return
	 */
	@RequestMapping("/submitResult")
	public String submitResult(HttpServletRequest request) {
		Long pkRoomId =0L;
		String phone="";
		// 从session中取出该用户的答案
		HttpSession session = request.getSession();
		Map<String, Object> pkResultMap = null;
		String pkResultJSON = null;
		String prePKResult = "";
		
		// 根据房间号把答案存入数据库
		if (session != null) {
			User user = (User)session.getAttribute(Constants.LOGIN_USER);
			phone = user.getPhone();
			pkRoomId = userService.getPKRoomIdByPhone(phone);
			
			pkResultMap = new HashMap<String, Object>();
			pkResultMap = (Map<String, Object>) session
					.getAttribute("pkResultMap");
			JSONObject jsonObject = JSONObject.fromObject(pkResultMap);
			pkResultJSON = jsonObject.toString();
			// {'fail':2,'success':1,'name':'张三','time':30000}
			// 先取出数据库中该房间的pkResult，再进行字符串拼接后存入数据库
			prePKResult = pkRoomService.getPKResultByPKRoomId(pkRoomId);
			if ("".equals(prePKResult) || prePKResult == null) {
				pkRoomService.updatePKResultByPKRoomId(pkResultJSON, pkRoomId);
			} else {
				// 拼接pkResult
				prePKResult = prePKResult + "," + pkResultJSON;
				System.out.println(prePKResult);
				pkRoomService.updatePKResultByPKRoomId(prePKResult, pkRoomId);
			}
		}
		return "submitSuccess";
	}

	/**
	 * 获取PK时间
	 * @return
	 */
	@RequestMapping("/PKData")
	public Map<String, Object> replyPKData() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("time", "300000");
		resultMap.put("count", "5");
		return resultMap;
	}

	/**
	 * 当双方匹配成功时，从数据库中取出题目
	 * http://localhost:8888/api/PK/PKQuestions?pkRoomId=1
	 * @param pkRoomId
	 * @return
	 */
	@RequestMapping("/PKQuestions")
	public String replyPKQuestionsByPKRoomID(Long pkRoomId) {
		String questions = pkRoomService.findQuestionsByPKRoomId(pkRoomId);
		return questions;
	}

	@RequestMapping("/getCurrentUserPhone")
	public Map<String, Object> getPKUserInfo(HttpServletRequest request){
		ReplyPKUser pkUser = new ReplyPKUser();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpSession session = request.getSession(true);
		//获取当前shiro中session的用户信息
		Subject subject = SecurityUtils.getSubject();
		Session session2 = subject.getSession();
		User user = (User)session2.getAttribute(Constants.LOGIN_USER);
		
		pkUser = userToPkUser(user);
		resultMap.put("user", pkUser);
		session.setAttribute(Constants.SESSION_USER, pkUser);
		return resultMap;
		
	}
	
	
	/**
	 * 获取当前用户的PK信息 http://localhost:8888/api/PK/getUserData?phone=110
	 * @param request
	 * @param phone
	 * @return
	 */
	@RequestMapping("/getUserData")
	public Map<String, Object> findPersonById(HttpServletRequest request,
			@RequestParam("phone") String phone) {
		ReplyPKUser pkUser = new ReplyPKUser();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpSession session = request.getSession(true);
		User user = (User) userService.findUserByPhone(phone);
		pkUser = userToPkUser(user);
		resultMap.put("user", pkUser);
		session.setAttribute(Constants.SESSION_USER, pkUser);
		return resultMap;
		
	}

	/**
	 * 发送PK的广告信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/adInfo")
	public Map<String, Object> replyAdInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		CopyOnWriteArrayList<String> adStringList = (CopyOnWriteArrayList<String>) session
				.getAttribute("adString");
		map.put("content", adStringList);
		return map;
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
		pkUser.setPkRate(user.getPKRate());
		pkUser.setPKInfo(user.getPKRecord());
		return pkUser;
	}

}
