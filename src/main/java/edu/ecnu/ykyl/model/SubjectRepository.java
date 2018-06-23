package edu.ecnu.ykyl.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

	// SELECT sb_name,sb_shortcut,sb_title FROM t_subject WHERE id=3;
	@Query(value = "FROM Subject WHERE id=?")
	public Subject findSubjectById(long id);

	@Query(value = "SELECT * FROM t_subject WHERE id "
			+ "IN(SELECT subjects FROM t_grade_subject WHERE grades=?1)", nativeQuery = true)
	public List<Subject> findSubjectsByGrade(Long gid);

	@Query(value = "SELECT t_question.id,t_question.question_content,t_question.question_type FROM t_subject,t_knowledge_point,t_question"
			+ " WHERE t_subject.id=t_knowledge_point.kp_subject_id"
			+ " AND t_knowledge_point.id=t_question.qu_knowledge_point_id"
			+ " AND t_subject.id=?1", nativeQuery = true)
	public List<Object> findQuestionsBySubjectId(Integer sid);

}
