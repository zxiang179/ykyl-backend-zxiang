package edu.ecnu.ykyl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ecnu.ykyl.entity.Comment;
import edu.ecnu.ykyl.model.QuestionCommentsRepository;

@Service
public class QuestionCommentsService {
	
	@Autowired
	private QuestionCommentsRepository questionCommentsRepository;
	
    //获得每个问题对应的评论
	public List<Comment> findCommentsByQueId(long questionAndAnswerId){
		return questionCommentsRepository.findCommentsByQueId(questionAndAnswerId);
	}
	
	//更新问题的点赞数
	public void updateSupportNum(long commentID){
		int zan = questionCommentsRepository.findZanByCommentId(commentID);
		questionCommentsRepository.updateSupportNum(zan, commentID);
	}
	
	//提交评论内容
	public void saveComment(long qid,String commentContent,long userId,int zan,String grade,String subject,String username){
		questionCommentsRepository.addComment(commentContent, qid, userId, 0, grade, subject, username);
	}

}
