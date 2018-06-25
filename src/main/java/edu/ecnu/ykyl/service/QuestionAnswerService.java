package edu.ecnu.ykyl.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.entity.QuestionAnswer;
import edu.ecnu.ykyl.model.QuestionAnswerRepository;

@Service
public class QuestionAnswerService {
	
	@Autowired
	private QuestionAnswerRepository questionAnswerRepository;
	
	//根据年级、学科获取QuestionAnswer
	public List<QuestionAnswer> findQuestionAns(String grade, String subject) {
		List<QuestionAnswer> questionAnswers = questionAnswerRepository.findQuestionAnswersByGradeAndSubject(grade, subject);
		return questionAnswers;
	}

	// 获得所有的内容
	// 查找所有的QuestionAnswer
	public List<QuestionAnswer> findAllQuestionAns() {
		List<QuestionAnswer> allQuestionAnswers = questionAnswerRepository.findAllQuestionAnswers();
		return allQuestionAnswers;
	}

	// 保存提出的问题
	public void askContentSave(String questionDetail, long userID,
			long grade, String subject,int count,String username) {
		questionAnswerRepository.addQuestionAnswerRecord(questionDetail, userID, grade, subject, count, username);
	}
	
	

}
