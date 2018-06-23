package edu.ecnu.ykyl.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Answer;
import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.Question;
import edu.ecnu.ykyl.entity.Status;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.replyentity.ReplyPKUser;
import edu.ecnu.ykyl.service.AnswerService;
import edu.ecnu.ykyl.service.QuestionService;
import edu.ecnu.ykyl.service.UserService;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

	@Autowired
	private AnswerService answerService;
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;

	/**
	 * 将答案批改后的结果存入session
	 * 
	 * @param request
	 * @param qid
	 * @param answer
	 */
	@RequestMapping("/recordPKJudgement")
	public void recordPKJudgement(HttpServletRequest request, Long qid,
			Long answer) {
		boolean judgementResult = answerService.PKAnswerJudgement(qid, answer);
		HttpSession session = request.getSession();
		if (session != null) {
			Map<String, Object> pkResultMap = (Map<String, Object>) session
					.getAttribute("pkResultMap");
			if (pkResultMap != null) {
				int success = (int) pkResultMap.get("success");
				int fail = (int) pkResultMap.get("fail");
				if (judgementResult) {
					pkResultMap.put("success", success + 1);
				} else {
					pkResultMap.put("fail", fail + 1);
				}
				session.setAttribute("pkResultMap", pkResultMap);
			} else {
				ReplyPKUser pkUser = (ReplyPKUser) session
						.getAttribute(Constants.SESSION_USER);
				pkResultMap = new HashMap<String, Object>();
				pkResultMap.put("name", pkUser.getName());
				
				if(judgementResult){
					pkResultMap.put("success", 1);
					pkResultMap.put("fail", 0);
				}else{
					pkResultMap.put("fail", 1);
					pkResultMap.put("success", 0);
				}
				
				pkResultMap.put("time", 30000);
				session.setAttribute("pkResultMap", pkResultMap);
			}
		} else {
			System.out.println("session不存在");
		}
	}

	// 实现批改用户答案（前端发送用户答案和题号）
	// 将用户在pk模块作答的答案保存在该用户的session中
	@RequestMapping("/savePKUserReplyAnswer")
	public void savePKUserReplyAnswer(HttpServletRequest request,
			String answer, Long qid) {
		Status[] status = null;
		String stdAnswer = answerService.findStdAnswer(qid);
		HttpSession session = request.getSession();
		String pKAnswerRecord = (String) session.getAttribute("pKAnswerRecord");
		// 获取user对象
		User user = (User) session.getAttribute("replyUser");
		String replyJudgement = "";
		String record = "";
		if (stdAnswer.equals(answer)) {
			replyJudgement = "true";
		} else {
			replyJudgement = "false";
		}
		// 小明：true,false,false,true
		if ("".equals(pKAnswerRecord) || null == pKAnswerRecord) {
			record += user.getName();
			record = record + ":" + replyJudgement;
		} else {
			record = record + "," + replyJudgement;
		}
		session.setAttribute("pKAnswerRecord", record);
	}

	// test-answer.json
	@RequestMapping("/replyJudgement")
	public Map<String, Object> replyJudgeMent(HttpServletRequest request) {
		Map<String, Object> replyMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		replyMap = (Map<String, Object>) session.getAttribute("replyJudgement");
		return replyMap;
	}

	// Object {currentQuestionId: "9", resAnswer: "{"9":0}", testTime: "9001"}
	// {"currentQuestionId":"19","resAnswer":"{9:2,10:2,11:2,12:2,13:2,14:2,15:2,16:2,17:2,18:2,19:2}","testTime":"25001"}
	@RequestMapping("/replyFakeJudgement")
	public void replyFakeJudgeMent(HttpServletRequest request,
			String submitResult,
			String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		//从session中获取用户
		HttpSession session = request.getSession();
    	User user = (User)session.getAttribute(Constants.LOGIN_USER);
    	long uid = user.getId();
    	
		System.out.println(submitResult);
		System.out.println("=========================================");
		map = answerService.replyAnswer(submitResult,uid,type);
		session.setAttribute("replyJudgement", map);
		map2 = (Map<String, Object>) session.getAttribute("replyJudgement");

	}

	@RequestMapping("/addAnswer")
	public String addAnswer() {
		Answer answer = new Answer();
		User user = new User();
		Question question = new Question();
		user = userService.findUserByPhone("13127879086");
		question = questionService.findByQuestionId(7);
		answer.setAnswer("D");
		answer.setAnQuestionId(question);
		answer.setUserId(user);
		answer.setId(6L);

		answerService.save(answer);
		return "add success";
	}

}
