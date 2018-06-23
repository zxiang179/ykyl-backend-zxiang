package edu.ecnu.ykyl.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_label")
public class Label extends EntityId {

	@Column(name = "lb_name")
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "quLabelId")
	private Set<Question> set = new HashSet<Question>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Question> getSet() {
		return set;
	}

	public void setSet(Set<Question> set) {
		this.set = set;
	}

}
