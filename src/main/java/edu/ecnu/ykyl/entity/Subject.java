package edu.ecnu.ykyl.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_subject")
public class Subject extends EntityId {

	@Column(name = "sb_name")
	private String sbName;

	@JsonIgnore
	@OneToMany(mappedBy = "kpSubject")
	private List<KnowledgePoint> knowledgePoints = new ArrayList<KnowledgePoint>();

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "zx_grade_subject", joinColumns = { @JoinColumn(name = "subjects", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "grades", referencedColumnName = "id") })
	private List<Grade> grades = new ArrayList<Grade>();

	@Column(name = "sb_shortcut")
	private String shortcut;

	@Column(name = "sb_title")
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortcut() {
		return shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	public String getSbName() {
		return sbName;
	}

	public void setSbName(String sbName) {
		this.sbName = sbName;
	}

	public List<KnowledgePoint> getKnowledgePoints() {
		return knowledgePoints;
	}

	public void setKnowledgePoints(List<KnowledgePoint> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}

	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

}
