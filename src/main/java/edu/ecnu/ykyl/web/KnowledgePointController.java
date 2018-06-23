package edu.ecnu.ykyl.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.KnowledgePoint;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.service.KnowledgePointService;

@RestController
@RequestMapping("/api/knowledgePoint")
public class KnowledgePointController {

	@Autowired
	private KnowledgePointService knowledgePointService;

	@RequestMapping("/findAllKnowledgePoint")
	public List<KnowledgePoint> findAll() {
		return knowledgePointService.findAllKnowledgePoint();
	}

	@RequestMapping("/findOneKnowledgePoint")
	// test-page-meta.json
	// id:sid或kid type:random/normal
	public Map<String, Object> findOneSubject(int id, String type,HttpServletRequest request) {
		HttpSession session = request.getSession();
    	User user = (User)session.getAttribute(Constants.LOGIN_USER);
    	long uid = user.getId();
		return knowledgePointService.findOneKnowledgePoint(id, type,uid);
	}

	@RequestMapping("/resetOneHist")
	public Map<String, Object> resetOneHist(int kid,HttpServletRequest request) {
		//从session中获取用户id并查找历史记录
		HttpSession session = request.getSession();
    	User user = (User)session.getAttribute(Constants.LOGIN_USER);
    	long uid = user.getId();
		return knowledgePointService.resetUserHist(kid,uid);
	}

	@RequestMapping("/findKPNames")
	public List<String> findKPNamesBySubject(int id) {
		return knowledgePointService.findKPNameBySubjectId(id);
	}

}
