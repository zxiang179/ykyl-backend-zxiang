package edu.ecnu.ykyl.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.entity.Grade;
import edu.ecnu.ykyl.entity.KnowledgePoint;
import edu.ecnu.ykyl.entity.Subject;
import edu.ecnu.ykyl.model.GradeRepository;
import edu.ecnu.ykyl.model.KnowledgePointRepository;
import edu.ecnu.ykyl.model.SubjectRepository;
import edu.ecnu.ykyl.replyentity.ReplyGrade;
import edu.ecnu.ykyl.replyentity.ReplyKnowledgePoint;
import edu.ecnu.ykyl.replyentity.ReplyMenuSubject;
import edu.ecnu.ykyl.replyentity.ReplySubject;

@Service
public class GradeService {

	@Autowired
	private GradeRepository gradeRepository;
	@Autowired
	private KnowledgePointRepository knowledgePointRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	public List<Grade> findGrades() {
		return gradeRepository.findGrades();
	}

	// tm-menu-subject.json
	public ReplyMenuSubject findMenuSubjectInfo() {
		ReplyKnowledgePoint replyKnowledgePoint = null;
		ReplySubject replySubject = null;
		ReplyGrade replyGrade = null;
		ReplyMenuSubject replyMenuSubject = null;
		List<ReplyKnowledgePoint> replyKnowledgePoints = null;
		List<ReplySubject> replySubjects = null;
		List<ReplyGrade> replyGrades = new ArrayList<ReplyGrade>();
		Long gid = 0L;
		String gName = "";

		Long sid = 0L;
		String sName = "";
		String sShortcut = "";

		Long kid = 0L;
		String kName = "";
		String kShortCut = "";
		// 查找出所有的年纪
		List<Grade> grades = gradeRepository.findGrades();
		// 根据年级查找出所有的学科科目
		for (Grade g : grades) {
			gid = g.getId();
			gName = g.getName();

			replyGrade = new ReplyGrade();
			replyGrade.setId(gid);
			replyGrade.setGrade(gName);
			replySubjects = new ArrayList<ReplySubject>();
			// List<Subject> subjects = g.getSubjects();
			List<Subject> subjects = subjectRepository.findSubjectsByGrade(gid);

			for (Subject s : subjects) {
				sid = s.getId();
				sName = s.getSbName();
				sShortcut = s.getShortcut();

				replySubject = new ReplySubject();
				replySubject.setId(sid);
				replySubject.setName(sName);
				// List<KnowledgePoint> knowledgePoints =
				// s.getKnowledgePoints();
				List<KnowledgePoint> knowledgePoints = knowledgePointRepository
						.findKnowledgePointBySubjectId(sid);

				replyKnowledgePoints = new ArrayList<ReplyKnowledgePoint>();
				for (KnowledgePoint k : knowledgePoints) {
					kid = k.getId();
					kName = k.getKpName();

					replyKnowledgePoint = new ReplyKnowledgePoint();
					replyKnowledgePoint.setId(kid);
					replyKnowledgePoint.setName(kName);
					replyKnowledgePoints.add(replyKnowledgePoint);
				}
				replySubjects.add(replySubject);
				replySubject.setKnowledgePoints(replyKnowledgePoints);

			}
			replyGrade.setReplySubjects(replySubjects);
			replyGrades.add(replyGrade);
		}
		// 根据某个学科的id查询出它所有的知识点
		// knowledgePointRepository.findKPNameBySubjectId();
		replyMenuSubject = new ReplyMenuSubject();
		replyMenuSubject.setBank("一课一练");
		replyMenuSubject.setReplyGrade(replyGrades);
		return replyMenuSubject;
	}

}
