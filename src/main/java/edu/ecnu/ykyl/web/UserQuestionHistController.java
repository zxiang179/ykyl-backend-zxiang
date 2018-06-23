package edu.ecnu.ykyl.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.UserQuestionHist;
import edu.ecnu.ykyl.service.UserQuestionHistService;

@RestController
@RequestMapping("/api/UserQuestionHist")
public class UserQuestionHistController {

	@Autowired
	private UserQuestionHistService userQuestionHistService;

	@RequestMapping("/addHist")
	public String addHist(String hist, Long uid) {
		userQuestionHistService.addHistByUserId(hist, uid);
		return "add success";
	}

	@RequestMapping("/findSelectedHist")
	public List<Map<String, Object>> findSelectedHist(Long qhid) {
		return userQuestionHistService.findSelectedHist(qhid);
	}

	@RequestMapping("/findUserQuestionHistById")
	public UserQuestionHist findUserQuestionHistById(Long uid) {
		return userQuestionHistService.findUserQuestionHistById(uid);
	}

	@RequestMapping("/addMoreHistByUserId")
	public String addMoreHistByUserId(String hist, Long uid) {
		userQuestionHistService.addMoreHistByUserId(hist, uid);
		return "add More Success";
	}

	@RequestMapping("/updateHistByUserId")
	public String updateHistByUserId(String hist, Long uid) {
		userQuestionHistService.updateHistByUserId(hist, uid);
		return "update success";
	}

}
