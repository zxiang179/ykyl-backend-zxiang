package edu.ecnu.ykyl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "test_ecnuer")
public class Ecnuer extends EntityId {

	@Column
	private String ecnuerName;

	@ManyToOne
	@JoinColumn(name = "t_yx")
	private Unit unit;

	public String getEcnuerName() {
		return ecnuerName;
	}

	public void setEcnuerName(String ecnuerName) {
		this.ecnuerName = ecnuerName;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Ecnuer(String ecnuerName, Unit unit) {
		super();
		this.ecnuerName = ecnuerName;
		this.unit = unit;
	}

	public Ecnuer() {
	}

}
