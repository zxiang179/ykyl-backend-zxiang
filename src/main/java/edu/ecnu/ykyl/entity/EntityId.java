package edu.ecnu.ykyl.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Each entity of any table has a unique long identifier and
 * <code>EntityId</code> is used to provided such an ID.
 * 
 * @author xulinhao
 */

@MappedSuperclass
public abstract class EntityId {

	@Id
	@GeneratedValue
	long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
