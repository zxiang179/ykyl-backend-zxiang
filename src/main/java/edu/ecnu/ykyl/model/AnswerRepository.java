package edu.ecnu.ykyl.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.Answer;

@Repository
@Transactional
public interface AnswerRepository extends JpaRepository<Answer, Long> {

	@Modifying
	@Query(value = "insert into t_answer(id,answer,question_id,user_id) values(?1,?2,?3,?4)", nativeQuery = true)
	public void addAnswer(Long id, String answer, Long qid, Long uid);

	@Query(value = "SELECT std_answer FROM t_question WHERE id=?", nativeQuery = true)
	public String findStdAnswer(Long qid);

}
