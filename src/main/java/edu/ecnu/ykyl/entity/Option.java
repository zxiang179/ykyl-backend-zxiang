package edu.ecnu.ykyl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_option")
public class Option extends EntityId {

	// ABCD
	@Column(name = "op_code")
	private String code;

	// 题支
	@Column(name = "op_content")
	private String content;

	@ManyToOne
	@JoinColumn(name = "op_questionId", nullable = false)
	@JsonIgnore
	private Question opQestionId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Question getOpQestionId() {
		return opQestionId;
	}

	public void setOpQestionId(Question opQestionId) {
		this.opQestionId = opQestionId;
	}

	@Override
	public String toString() {
		return "Option [code=" + code + ", content=" + content
				+ ", opQestionId=" + opQestionId + "]";
	}

}
