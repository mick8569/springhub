package com.mjeanroy.springhub.models.entities;

import com.mjeanroy.springhub.models.AbstractModel;
import com.mjeanroy.springhub.models.Model;

import javax.persistence.MappedSuperclass;

/**
 * Abstract implementation of jpa entity.
 * This abstraction defines commons methods using getId implementation: toString, equals and hashCode.
 *
 * TODO use generic type for id
 * TODO fix hashCode when getId is null: hashCode value should not changed once object is created.
 */
@MappedSuperclass
public abstract class AbstractEntity extends AbstractModel implements Model, JPAEntity {

	@Override
	public String toString() {
		return String.format("%s{id=%s}", getClass().getSimpleName(), getId());
	}

	@Override
	public int hashCode() {
		Long id = getId();
		return id == null ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o != null && getClass().equals(o.getClass())) {
			AbstractEntity entity = (AbstractEntity) o;
			Long id1 = getId();
			Long id2 = entity.getId();
			return id1 != null && id2 != null && id1.equals(id2);
		}
		return false;
	}
}
