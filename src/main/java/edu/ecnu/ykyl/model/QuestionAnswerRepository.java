package edu.ecnu.ykyl.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.Question;
import edu.ecnu.ykyl.entity.QuestionAnswer;

@Transactional
@Repository
public interface QuestionAnswerRepository  extends JpaRepository<Question, Long> {
	
	@Modifying
	@Query(value = "INSERT INTO t_question_ans(question_detail,user_id,grade,subject,count,user_name) VALUES(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
	public void addQuestionAnswerRecord(String questionDetail,long userID,long grade,String subject,int count,String username);
	
	@Query(value = "select * from t_question_ans",nativeQuery = true)
	public List<QuestionAnswer> findAllQuestionAnswers();
	
	@Query(value = "SELECT * FROM t_question_ans WHERE grade = ?1 AND subject = ?2",nativeQuery = true)
	public List<QuestionAnswer> findQuestionAnswersByGradeAndSubject(String grade,String subject);

}
