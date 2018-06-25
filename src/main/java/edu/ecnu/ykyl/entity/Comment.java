package edu.ecnu.ykyl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_comment")
public class Comment extends EntityId{
	
	@Column(name = "comment_detail")
	private String commentDetail;
	
	@ManyToOne
	@JoinColumn(name = "question_ans_id")
	private QuestionAnswer questionAnswer;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "zan")
	private Integer zan;
	
	@ManyToOne
	@JoinColumn(name = "grade")
	private Grade grade;
	
	@ManyToOne
	@JoinColumn(name = "subject")
	private Subject subject;
	
	@Column(name = "cme_name")
	private String commentName;

	public String getCommentDetail() {
		return commentDetail;
	}

	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
	}

	public QuestionAnswer getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(QuestionAnswer questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getZan() {
		return zan;
	}

	public void setZan(Integer zan) {
		this.zan = zan;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getCommentName() {
		return commentName;
	}

	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}
	
	

}
