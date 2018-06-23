package edu.ecnu.ykyl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_answer")
public class Answer extends EntityId {

	@ManyToOne
	@JoinColumn(name = "an_userId")
	private User userId;

	@ManyToOne
	@JoinColumn(name = "an_questionId")
	private Question anQuestionId;

	@Column(name = "answer")
	private String answer;

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Question getAnQuestionId() {
		return anQuestionId;
	}

	public void setAnQuestionId(Question anQuestionId) {
		this.anQuestionId = anQuestionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
