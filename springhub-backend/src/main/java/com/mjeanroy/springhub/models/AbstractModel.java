package com.mjeanroy.springhub.models;

import static java.lang.String.format;

import java.io.Serializable;

/**
 * Partial implementation of model object.
 * The only implemented method is {@link Model#isNew()} that
 * return true if id is null.
 *
 * @param <PK> Generic type of id.
 *
 * TODO fix implementation when id is null: hashCode should not change once object is created...
 */
public abstract class AbstractModel<PK extends Serializable> implements Model<PK> {

	@Override
	public boolean isNew() {
		return getId() == null;
	}

	@Override
	public String toString() {
		return format("%s{id=%s}", getClass().getSimpleName(), getId());
	}

	@Override
	public int hashCode() {
		PK id = getId();
		return id == null ? 0 : id.hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o != null && o.getClass().equals(getClass())) {
			Model<PK> model = (Model<PK>) o;
			PK id1 = getId();
			PK id2 = model.getId();
			return id1 != null && id2 != null && id1.equals(id2);
		}
		return false;
	}
}
