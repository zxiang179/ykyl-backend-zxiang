package edu.ecnu.ykyl.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.UserQuestionHist;

@Repository
@Transactional
public interface UserQuestionHistRepository extends
		JpaRepository<UserQuestionHist, Long> {

	// INSERT INTO t_user_question_hist(id,qh_user_question_hist,qh_user_id)
	// VALUES(1,'xxx',1);
	@Modifying
	@Query(value = "insert into t_user_question_hist(qh_user_question_hist,qh_user_id) "
			+ "values(?1,?2)", nativeQuery = true)
	public void addQuestionHist(String hist, Long uid);

	// SELECT qh_user_question_hist FROM t_user_question_hist WHERE id=2;
	@Query(value = "SELECT qh_user_question_hist FROM t_user_question_hist WHERE qh_user_id=?1", nativeQuery = true)
	public String findSelectedHist(Long qhid);

	// UPDATE t_user_question_hist SET qh_user_question_hist='kasd' WHERE id=1;
	@Modifying
	@Query(value = "UPDATE t_user_question_hist SET qh_user_question_hist=?1 WHERE qh_user_id=?2", nativeQuery = true)
	public void updateHistByUserId(String hist, Long qh_user_id);

	@Query(value = "select * from t_user_question_hist where id=?1", nativeQuery = true)
	public UserQuestionHist findUserQuestionHistById(Long uid);

	@Query(value = "UPDATE t_user_question_hist SET qh_user_question_hist='' WHERE qh_user_id=?1", nativeQuery = true)
	@Modifying
	public void resetUserQuestionHistById(Long uid);

}
