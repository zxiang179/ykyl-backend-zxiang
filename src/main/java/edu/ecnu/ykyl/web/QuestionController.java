package edu.ecnu.ykyl.web;

import static edu.ecnu.ykyl.Constant.API_QUESTION;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.Option;
import edu.ecnu.ykyl.entity.Question;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.service.OptionService;
import edu.ecnu.ykyl.service.QuestionService;

@RestController
@RequestMapping(API_QUESTION)
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private OptionService optionService;
	
	@RequestMapping("/importQuestions")
	public String importQuestions(){
		questionService.importQuestions();
		return "success import";
	}

	@RequestMapping("/replyPKQuestions")
	public Map<String, Object> replyPKQuestions(HttpServletRequest request,
			Integer sid, Integer count) {
		Map<String, Object> pkQuestions = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		// pkQuestions =
		// (HashMap<String,Object>)session.getAttribute("pKQuestions");
		// if(null==pkQuestions){
		pkQuestions = questionService.replyPKQuestions(sid, count);
		// }
		// session.setAttribute("pKQuestions", pkQuestions);
		return pkQuestions;
	}

	@RequestMapping("/replyPKExamMetaInfo")
	public Map<String, Object> replyPKExamMetaInfo(Integer sid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("time", 10000);
		map.put("count", 30);
		return map;
	}

	@RequestMapping("/findForwardQuestions")
	@ResponseBody
	// test-page-1.json
	public Map<String, Object> findForwardQuestions(
			@RequestParam("qid") int currentId,
			int firstFlag,
			String type,
			HttpServletRequest request) {
		//从session中获取用户id并查找历史记录
		HttpSession session = request.getSession();
    	User user = (User)session.getAttribute(Constants.LOGIN_USER);
    	long uid = user.getId();
		Map<String, Object> replyMap = questionService.findForwardQuestions(
				currentId, 
				firstFlag,
				type,
				uid);
		return replyMap;
	}

	@RequestMapping("/findBackwardQuestions")
	@ResponseBody
	// test-page-1.json
	public Map<String, Object> findBackwardQuestions(
			@RequestParam("qid") int currentId, String type) {
		Map<String, Object> replyMap = questionService.findBackwardQuestions(
				currentId, type);
		return replyMap;
	}

	@RequestMapping("/specificAnswer")
	// specific-answer.json
	public Map<String, Object> findSpecificAnswer(int qid) {
		Map<String, Object> map = questionService.findSpecificAnswer(qid);
		return map;
	}

	@RequestMapping("/findQuestionById")
	public Map<String, Object> t1(long qid) {
		Map<String, Object> map = new HashMap<String, Object>();
		Question question = questionService.findByQuestionId(qid);
		List<Option> options = optionService.findOptions(qid);
		map.put("content", question.getContent());
		map.put("std_answer", question.getStdAnswer());
		map.put("options", options);
		return map;
	}

	@RequestMapping("/findAllQuestion")
	public Map<String, Object> t2() {
		List<Question> questions = questionService.findAllQuestion();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Q", questions);
		return map;
	}

	@RequestMapping("/findQuestionByRandom")
	public Set<Question> findQuestionByRandom(int count) {
		Set<Question> list = questionService.findQuestionByRandom(count);
		return list;
	}

	@RequestMapping("/findStdAnswerByAnswerId")
	public String findStdAnswerByAnswerId(Long id) {
		return questionService.findStdAnswerByAnswerId(id);
	}

	@RequestMapping("/findKpIdByQId")
	public int findKPIdByQuestionId(int qid) {
		return questionService.findKpIdByQId(qid);
	}

}
