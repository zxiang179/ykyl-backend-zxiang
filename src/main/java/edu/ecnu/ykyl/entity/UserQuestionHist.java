package edu.ecnu.ykyl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_user_question_hist")
public class UserQuestionHist extends EntityId {

	@ManyToOne
	@JoinColumn(name = "qh_userId")
	@JsonIgnore
	private User qhUserId;

	@Column(name = "qh_userQuestionHist")
	private String UserQuestionHist;

	public User getQhUserId() {
		return qhUserId;
	}

	public void setQhUserId(User qhUserId) {
		this.qhUserId = qhUserId;
	}

	public String getUserQuestionHist() {
		return UserQuestionHist;
	}

	public void setUserQuestionHist(String userQuestionHist) {
		UserQuestionHist = userQuestionHist;
	}

}
