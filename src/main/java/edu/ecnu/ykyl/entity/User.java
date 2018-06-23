package edu.ecnu.ykyl.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_User")
public class User extends EntityId {

	private String name;

	private String phone;

	private String password;

	private String gender;

	private String schoolDistrict;

	private String school;

	private String grade;

	private String PKRecord;// PKRecord记录用户的pk结果

	private String ChinesePKRecord; // 语文pk记录数

	private String MathPKRecord; // 数学pk记录数

	private String EnglishPKRecord; // 英语pk记录数

	private Double PKRate; // pk总胜率
	
	private Long pkRoomId;

	private String Avatar; // 用户头像路径

	// mappedBy中的字段名与副表中设置的变量名保持一致
	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Answer> myAnswer = new HashSet<Answer>();

	@OneToMany(mappedBy = "qhUserId", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<UserQuestionHist> userQuestionHist = new HashSet<UserQuestionHist>();

	public Set<UserQuestionHist> getUserQuestionHist() {
		return userQuestionHist;
	}

	public void setUserQuestionHist(Set<UserQuestionHist> userQuestionHist) {
		this.userQuestionHist = userQuestionHist;
	}

	public Set<Answer> getMyAnswer() {
		return myAnswer;
	}

	public void setMyAnswer(Set<Answer> myAnswer) {
		this.myAnswer = myAnswer;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSchoolDistrict() {
		return schoolDistrict;
	}

	public void setSchoolDistrict(String schoolDistrict) {
		this.schoolDistrict = schoolDistrict;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User() {
	}

	public String getPKRecord() {
		return PKRecord;
	}

	public void setPKRecord(String pKRecord) {
		PKRecord = pKRecord;
	}

	public String getChinesePKRecord() {
		return ChinesePKRecord;
	}

	public void setChinesePKRecord(String chinesePKRecord) {
		ChinesePKRecord = chinesePKRecord;
	}

	public String getMathPKRecord() {
		return MathPKRecord;
	}

	public void setMathPKRecord(String mathPKRecord) {
		MathPKRecord = mathPKRecord;
	}

	public String getEnglishPKRecord() {
		return EnglishPKRecord;
	}

	public void setEnglishPKRecord(String englishPKRecord) {
		EnglishPKRecord = englishPKRecord;
	}

	public Double getPKRate() {
		return PKRate;
	}

	public void setPKRate(Double pKRate) {
		PKRate = pKRate;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		Avatar = avatar;
	}

	public Long getPkRoomId() {
		return pkRoomId;
	}

	public void setPkRoomId(Long pkRoomId) {
		this.pkRoomId = pkRoomId;
	}
	
}
