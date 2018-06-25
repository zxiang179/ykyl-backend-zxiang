package edu.ecnu.ykyl.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.QuestionAnswer;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.service.QuestionAnswerService;

@RestController
@RequestMapping("/api/questionAnswer")
public class QuestionAnswerController {
	
	private QuestionAnswerService questionAnswerService;

	// 获得过滤后的内容
	// 根据grade和subject来查找QuestionAnswer
	@RequestMapping(value = "/findQuestionAns")
	public List<QuestionAnswer> findQuestionAns(String grade, String subject) {
		List<QuestionAnswer> questionAns = questionAnswerService.findQuestionAns(grade, subject);
		return questionAns;
	}

	// 获得所有的内容
	// 查找所有的QuestionAnswer
	@RequestMapping(value = "/findAllQuestionAns")
	public List<QuestionAnswer> findAllQuestionAns() {
		List<QuestionAnswer> questionAns = questionAnswerService.findAllQuestionAns();
		return questionAns;
	}

	// 保存提出的问题
	@RequestMapping("/addQuestionAns")
	public void askContentSave(long grade, String subject,
			String questionDetail, HttpServletRequest request) {
		Map<String,Integer> map = new HashMap<String,Integer>();
    	HttpSession session = request.getSession();
    	User user = (User)session.getAttribute(Constants.LOGIN_USER);
    	long uid = user.getId();
    	String username = user.getName();
    	int count = 0;
    	questionAnswerService.askContentSave(questionDetail, uid, grade, subject, count, username);
	}

}
