package edu.ecnu.ykyl.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import edu.ecnu.ykyl.model.OptionRepository;
import edu.ecnu.ykyl.replyentity.ReplyQuestionStyle;

@SuppressWarnings("rawtypes")
@Component
public class QuestionMapper implements RowMapper {

	@Autowired
	private OptionRepository optionRepository;

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		ReplyQuestionStyle replyQuestionStyle = new ReplyQuestionStyle();

		long qid = rs.getLong("ID");
		replyQuestionStyle.setQuestionId(qid);
		replyQuestionStyle.setQuestionType(rs.getInt("question_type"));
		replyQuestionStyle.setQuestionContent(rs.getString("question_content"));

		// 查出该题的四个选项
		// options = optionRepository.findOptions(qid);
		// for(Option o:options){
		// replyOptionStyle=new ReplyOptionStyle();
		// replyOptionStyle.setCode(o.getCode());
		// replyOptionStyle.setContent(o.getContent());
		// optionList.add(replyOptionStyle);
		// }
		// replyQuestionStyle.setOptions(optionList);

		return replyQuestionStyle;
	}

}
