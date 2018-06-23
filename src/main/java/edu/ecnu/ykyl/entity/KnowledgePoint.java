package edu.ecnu.ykyl.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_knowledge_point")
public class KnowledgePoint extends EntityId {

	@Column(name = "kp_name")
	private String kpName;

	@Column(name = "kp_shortcut")
	private String kpShortCut;

	@ManyToOne
	@JoinColumn(name = "kp_subjectId")
	@JsonIgnore
	private Subject kpSubject;

	@OneToMany(mappedBy = "quKnowledgePointId")
	@JsonIgnore
	private Set<Question> set = new HashSet<Question>();

	public String getKpShortCut() {
		return kpShortCut;
	}

	public void setKpShortCut(String kpShortCut) {
		this.kpShortCut = kpShortCut;
	}

	public Set<Question> getSet() {
		return set;
	}

	public void setSet(Set<Question> set) {
		this.set = set;
	}

	public String getKpName() {
		return kpName;
	}

	public void setKpName(String kpName) {
		this.kpName = kpName;
	}

	public Subject getKpSubject() {
		return kpSubject;
	}

	public void setKpSubject(Subject kpSubject) {
		this.kpSubject = kpSubject;
	}

}
