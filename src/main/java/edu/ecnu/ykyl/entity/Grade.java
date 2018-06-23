package edu.ecnu.ykyl.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "t_grade")
public class Grade extends EntityId implements Serializable {

	@Column(name = "g_name")
	private String name;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "zx_grade_subject", joinColumns = { @JoinColumn(name = "grades", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "subjects", referencedColumnName = "id") })
	private List<Subject> subjects = new ArrayList<Subject>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
