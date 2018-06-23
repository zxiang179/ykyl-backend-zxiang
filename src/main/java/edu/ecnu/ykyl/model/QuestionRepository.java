package edu.ecnu.ykyl.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.Question;

@Transactional
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query(value = "from Question where id=?1")
	Question findByQuestionId(long id);

	@Modifying
	@Query("delete from Question where id=?1")
	void deleteById(Long id);

	@Query(value = "select count(*) from Question")
	int findCount();

	@Query(value="SELECT COUNT(*) FROM t_question WHERE t_question.qu_knowledge_point_id=?1", nativeQuery = true)
	Long findCountByKP(int kid);

	@Query(value = "SELECT t_question.std_answer FROM t_question WHERE t_question.id="
			+ "(SELECT question_id FROM t_answer WHERE t_answer.id=?1)", nativeQuery = true)
	// 根据答题的id号查找该题对应的标准答案
	public String findStdAnswerByAnswerId(Long id);

	// SELECT zx_question.std_answer FROM zx_question WHERE zx_question.id=2;
	// 根据questionId 查找stdAnswer
	@Query(value = "SELECT t_question.std_answer FROM t_question WHERE t_question.id=?1", nativeQuery = true)
	public String findStdAnswerByQuestionId(int id);

	//根据题目id查找所属知识点
	@Query(value = "SELECT qu_knowledge_point_id FROM t_question WHERE id=?1", nativeQuery = true)
	public int findKPIdByQuestionId(int qid);
	
	//根据题目id查找所属知识点的相对id
	@Query(value="SELECT t_question.kprelative_id FROM t_question WHERE t_question.id=?1",nativeQuery=true)
	public int findKPRelativeIdByQid(int qid);
	
	//SELECT id FROM t_question WHERE t_question.qu_knowledge_point_id=5 AND t_question.kprelative_id=15
    @Query(value="SELECT t_question.id FROM t_question WHERE t_question.qu_knowledge_point_id=?1 AND t_question.kprelative_id=?2",nativeQuery=true)
	public int findQuestionIdByKPIdAndKPRelativeId(int kid,int kpRelativeId);
	
	@Query(value = "SELECT * FROM t_question WHERE id>=?1", nativeQuery = true)
	public List<Question> findQuestionsFromLatestId(int id);
	
	@Query(value = "SELECT * FROM t_question WHERE t_question.qu_knowledge_point_id=?1 AND t_question.kprelative_id>?2", nativeQuery = true)
	public List<Question> findQuestionsByKidFromLatestId(int kid,int relativeId);
	
	@Query(value="SELECT * FROM t_question WHERE t_question.id=?1",nativeQuery=true)
	public List<Question> findQuestionByQid(int qid);
	
//	INSERT INTO t_question(kprelative_id,question_content,question_analyse,question_type,std_answer,qu_knowledge_point_id) VALUES (1,'3是几的3倍？正确的算式是（）','题目过于简单，请自行分析',0,'A',1);
	@Modifying
	@Query(value = "INSERT INTO t_question(kprelative_id,question_content,question_analyse,question_type,std_answer,qu_knowledge_point_id) VALUES (?1,?2,?3,?4,?5,?6)",nativeQuery=true)
	public void insertQuestion(int kprelativeId,String questionContent,String questionAnalyse,int questionType,String stdAns,int quKnowledgePointId);
	
}
