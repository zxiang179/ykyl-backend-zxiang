package edu.ecnu.ykyl.replyentity;

import java.util.List;

public class ReplyMenuSubject {

	private String bank;

	private List<ReplyGrade> replyGrades;

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public List<ReplyGrade> getReplyGrade() {
		return replyGrades;
	}

	public void setReplyGrade(List<ReplyGrade> replyGrades) {
		this.replyGrades = replyGrades;
	}

}
