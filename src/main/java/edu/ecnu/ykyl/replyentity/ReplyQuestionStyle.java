package edu.ecnu.ykyl.replyentity;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ReplyQuestionStyle implements Serializable {

	private int questionType;

	private List<ReplyOptionStyle> options;

	private Long questionId;

	private String questionContent;

	private Long UIrelativeId;
	
	public Long getUIrelativeId() {
		return UIrelativeId;
	}

	public void setUIrelativeId(Long uIrelativeId) {
		UIrelativeId = uIrelativeId;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public List<ReplyOptionStyle> getOptions() {
		return options;
	}

	public void setOptions(List<ReplyOptionStyle> options) {
		this.options = options;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public ReplyQuestionStyle(int questionType, List<ReplyOptionStyle> options,
			Long questionId, String questionContent) {
		this.questionType = questionType;
		this.questionId = questionId;
		this.questionContent = questionContent;
	}

	public ReplyQuestionStyle() {
	}

	@Override
	public String toString() {
		return "ReplyQuestionStyle [questionType=" + questionType
				+ ", options=" + options + ", questionId=" + questionId
				+ ", questionContent=" + questionContent + ", UIrelativeId="
				+ UIrelativeId + "]";
	}
	
	


}
