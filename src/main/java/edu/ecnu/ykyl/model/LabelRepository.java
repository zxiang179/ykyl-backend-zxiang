package edu.ecnu.ykyl.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

	// SELECT lb_name FROM zx_label,zx_question
	// WHERE zx_label.id=zx_question.qu_label_id AND zx_question.id=2;
	@Query(value = "select lb_name from t_label,t_question where t_label.id=t_question.qu_label_id "
			+ "and t_question.id=?1", nativeQuery = true)
	public String findLabelByQuestionId(int qid);

}
