package edu.ecnu.ykyl.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "test_unit")
public class Unit extends EntityId {

	@Column
	private String unit_name;

	@JsonIgnore
	@OneToMany(mappedBy = "unit")
	private Set<Ecnuer> students = new HashSet<Ecnuer>();

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public Set<Ecnuer> getStudents() {
		return students;
	}

	public void setStudents(Set<Ecnuer> students) {
		this.students = students;
	}

	public Unit(String unit_name, Set<Ecnuer> students) {
		super();
		this.unit_name = unit_name;
		this.students = students;
	}

	public Unit() {
	}

}
