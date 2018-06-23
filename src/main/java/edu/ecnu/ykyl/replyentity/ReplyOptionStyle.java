package edu.ecnu.ykyl.replyentity;

public class ReplyOptionStyle {

	// ABCD
	private String code;

	// 提干
	private String content;

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

	@Override
	public String toString() {
		return "ReplyOptionStyle [code=" + code + ", content=" + content + "]";
	}

}
