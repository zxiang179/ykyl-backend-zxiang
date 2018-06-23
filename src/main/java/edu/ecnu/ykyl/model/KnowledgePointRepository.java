package edu.ecnu.ykyl.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.KnowledgePoint;

@Repository
public interface KnowledgePointRepository extends
		JpaRepository<KnowledgePoint, Long> {

	// SELECT kp_name FROM zx_knowledge_point,zx_question
	// WHERE zx_knowledge_point.id=zx_question.qu_knowledge_point_id AND
	// zx_question.id=1;

	@Query(value = "select kp_name from t_knowledge_point,t_question where "
			+ "t_knowledge_point.id=t_question.qu_knowledge_point_id AND t_question.id=?1", nativeQuery = true)
	// 根据题号查询所对应的题目知识点
	public String findKPNameByQuestionId(int id);

	/**
	 * 根据知识点kid号查询该知识点题目的数量
	 * @param sid
	 * @return
	 */
	@Query(value = "SELECT COUNT(*) FROM t_question,t_knowledge_point "
			+ "WHERE t_question.qu_knowledge_point_id= t_knowledge_point.id "
			+ "AND t_knowledge_point.id=?1", nativeQuery = true)
	public int findOneKPCountByKPId(int kid);

	// SELECT kp_name FROM zx_knowledge_point WHERE id=2;
	@Query(value = "SELECT kp_name FROM t_knowledge_point WHERE id=?1", nativeQuery = true)
	public String findSubjectNameById(int kid);

	// SELECT kp_name FROM zx_knowledge_point WHERE kp_subject_id=1;
	@Query(value = "SELECT kp_name FROM t_knowledge_point WHERE kp_subject_id=?1", nativeQuery = true)
	public List<String> findKPNameBySubjectId(int id);

	// SELECT COUNT(*) FROM zx_question WHERE zx_question.qu_knowledge_point_id=
	// (SELECT zx_knowledge_point.id FROM zx_knowledge_point WHERE
	// kp_name='古文练习');
	@Query(value = "select count(*) from t_question where t_question.qu_knowledge_point_id="
			+ "(select t_knowledge_point.id from t_knowledge_point where kp_name=?1)", nativeQuery = true)
	public Long countByKPName(String KPName);

	/*
	 * @Query(value="from KnowledgePoint") public List<KnowledgePoint>
	 * findAllKnowledge();
	 */

	@Query(value = "SELECT * FROM t_knowledge_point WHERE kp_subject_id=?1", nativeQuery = true)
	public List<KnowledgePoint> findKnowledgePointBySubjectId(Long sid);

}
