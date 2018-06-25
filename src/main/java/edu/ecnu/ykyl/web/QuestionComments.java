package edu.ecnu.ykyl.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Comment;
import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.service.QuestionCommentsService;

@RestController
@RequestMapping("/api/questionComments")
public class QuestionComments {
	
	@Autowired
	private QuestionCommentsService questionCommentsService;
	
	//获得每个问题对应的评论
	@RequestMapping("/findCommentsByQueId")
	public List<Comment> findCommentsByQueId(long qid){
		List<Comment> comments = questionCommentsService.findCommentsByQueId(qid);
		return comments;
	}
	
	//更新问题的点赞数
	@RequestMapping("/zan")
	public void updateSupportNum(long commentID){
		questionCommentsService.updateSupportNum(commentID);
	}
	
	//提交评论内容
	//前端修改
	@RequestMapping("/addComments")
	public void saveComment(long qid,String commentContent,String grade,String subject,HttpServletRequest request){
		Map<String,Integer> map = new HashMap<String,Integer>();
    	HttpSession session = request.getSession();
    	User user = (User)session.getAttribute(Constants.LOGIN_USER);
    	long uid = user.getId();
    	String username = user.getName();
		int zan = 0;
		questionCommentsService.saveComment(qid, commentContent, uid, zan, grade, subject, username);
	}
	

}
