package edu.ecnu.ykyl.model;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.Comment;

@Transactional
@Repository
public interface QuestionCommentsRepository extends
		JpaRepository<Comment, Serializable> {

	@Query(value = "SELECT * FROM t_comment WHERE question_ans_id = ?1", nativeQuery = true)
	public List<Comment> findCommentsByQueId(long questionAndAnswerId);

	@Modifying
	@Query(value = "UPDATE t_comment SET zan =?1 WHERE id = ?2", nativeQuery = true)
	public void updateSupportNum(int zan, long commentId);

	@Query(value = "SELECT zan FROM t_comment WHERE id = ?1", nativeQuery = true)
	public int findZanByCommentId(long commentId);

	@Modifying
	@Query(value = "INSERT INTO t_comment(comment_detail,question_ans_id,user_id,zan,grade,SUBJECT,cme_name) "
			+ "VALUE(?1,?2,?3,?4,?5,?6,?7))", nativeQuery = true)
	public void addComment(String commentDetail, long questionAnsId,
			long userId, int zan, String grade, String subject,
			String commentName);

}
