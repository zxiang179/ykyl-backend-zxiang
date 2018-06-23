package edu.ecnu.ykyl.web.websocket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.socket.WebSocketSession;

import edu.ecnu.ykyl.entity.EntityId;

@Entity
@Table(name = "t_PKRoom")
public class PKRoom extends EntityId {

	@Column(name = "pkRoomId")
	private Integer pkRoomId;

	@Column(name = "questions")
	private String questions;

	@Column(name = "firstPKUser")
	private String firstPKUser;

	@Column(name = "secondPKUser")
	private String secondPKUser;

	@Column(name = "pkResult")
	private String pkResult;

	@Transient
	private WebSocketSession[] webSocketSession;

	public WebSocketSession[] getWebSocketSession() {
		return webSocketSession;
	}

	public void setWebSocketSession(WebSocketSession[] webSocketSession) {
		this.webSocketSession = webSocketSession;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public Integer getPkRoomId() {
		return pkRoomId;
	}

	public void setPkRoomId(Integer pkRoomId) {
		this.pkRoomId = pkRoomId;
	}

	public String getFirstPKUser() {
		return firstPKUser;
	}

	public void setFirstPKUser(String firstPKUser) {
		this.firstPKUser = firstPKUser;
	}

	public String getSecondPKUser() {
		return secondPKUser;
	}

	public void setSecondPKUser(String secondPKUser) {
		this.secondPKUser = secondPKUser;
	}

	public String getPkResult() {
		return pkResult;
	}

	public void setPkResult(String pkResult) {
		this.pkResult = pkResult;
	}

}
