package edu.ecnu.ykyl.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Grade;
import edu.ecnu.ykyl.replyentity.ReplyMenuSubject;
import edu.ecnu.ykyl.service.GradeService;

@RestController
@RequestMapping("/api/grade")
public class GradeController {

	@Autowired
	private GradeService gradeService;

	@RequestMapping("/findGrades")
	public List<Grade> findGradeById() {
		return gradeService.findGrades();
	}

	@RequestMapping("/findMenuSubjectInfo")
	public ReplyMenuSubject findMenuSubjectInfo() {
		return gradeService.findMenuSubjectInfo();
	}

}
