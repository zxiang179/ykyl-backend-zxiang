package edu.ecnu.ykyl.replyentity;

public class ReplyPKUser {

	private String name;
	private String school;
	private String grade;
	private Double pkRate;
	private String avatar;
	// 记录用户的PKInfo
	private String PKInfo;

	public String getPKInfo() {
		return PKInfo;
	}

	public void setPKInfo(String pKInfo) {
		PKInfo = pKInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Double getPkRate() {
		return pkRate;
	}

	public void setPkRate(Double pkRate) {
		this.pkRate = pkRate;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "ReplyPKUser [name=" + name + ", school=" + school + ", grade="
				+ grade + ", pkRate=" + pkRate + ", avatar=" + avatar
				+ ", PKInfo=" + PKInfo + "]";
	}

}