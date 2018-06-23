package edu.ecnu.ykyl.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ecnu.ykyl.entity.Option;

@Repository
@Transactional
public interface OptionRepository extends JpaRepository<Option, Long> {

	@Query(value = "select op_content from t_option where op_question_id=?1", nativeQuery = true)
	public List<String> findOptionsByQuestionId(int qid);

	@Query(value = "from Option where op_question_id=?1")
	public List<Option> findOptions(Long qid);
	
//	INSERT INTO t_option(op_code,op_content,op_question_id) VALUES('A','3÷3',1);
	@Modifying
	@Query(value="INSERT INTO t_option(op_code,op_content,op_question_id) VALUES(?1,?2,?3)",nativeQuery = true)
	public void insertOption(String opCode,String opContent,int opQuestionId);

}
