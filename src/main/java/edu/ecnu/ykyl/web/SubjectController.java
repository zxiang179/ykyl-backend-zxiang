package edu.ecnu.ykyl.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.Subject;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.service.SubjectService;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@RequestMapping("/findQuestionsBySubjectId")
	public List<Object> findQuestionsBySubjectId(Integer sid) {
		return subjectService.findQuestionsBySubjectId(sid);
	}

	@RequestMapping("/findAllSubjects")
	// subject-list.json
	public List<Subject> findAllSubject() {
		return subjectService.findAllSubjects();
	}

	// subject-content-math.json
	@RequestMapping("/findSubjectInfoById")
	public Map<String, Object> findSubjectInfo(long sid,HttpServletRequest request) {
		//从session中获取用户id并查找历史记录
		HttpSession session = request.getSession();
    	User user = (User)session.getAttribute(Constants.LOGIN_USER);
    	long uid = user.getId();
		return subjectService.findSubjectInfo(sid,uid);
	}

}
