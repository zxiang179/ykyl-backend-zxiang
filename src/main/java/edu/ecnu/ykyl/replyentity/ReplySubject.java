package edu.ecnu.ykyl.replyentity;

import java.util.List;

public class ReplySubject {

	private Long id;

	private String name;

	private List<ReplyKnowledgePoint> knowledgePoints;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ReplyKnowledgePoint> getKnowledgePoints() {
		return knowledgePoints;
	}

	public void setKnowledgePoints(List<ReplyKnowledgePoint> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}

}
