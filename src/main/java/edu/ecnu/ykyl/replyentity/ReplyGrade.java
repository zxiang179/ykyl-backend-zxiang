package edu.ecnu.ykyl.replyentity;

import java.util.List;

public class ReplyGrade {

	private Long id;

	private String grade;

	List<ReplySubject> replySubjects;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public List<ReplySubject> getReplySubjects() {
		return replySubjects;
	}

	public void setReplySubjects(List<ReplySubject> replySubjects) {
		this.replySubjects = replySubjects;
	}

}
