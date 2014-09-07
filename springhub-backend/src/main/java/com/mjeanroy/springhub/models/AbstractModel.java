package com.mjeanroy.springhub.models;

import static java.lang.String.format;

/**
 * Partial implementation of model object.
 * The only implemented method is {@link Model#isNew()} that
 * return true if id is null.
 *
 * TODO use generic type for id
 * TODO fix implementation when id is null: hashCode should not change once object is created...
 */
public abstract class AbstractModel implements Model {

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
		Long id = getId();
		return id == null ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o != null && o.getClass().equals(getClass())) {
			Model model = (Model) o;
			Long id1 = getId();
			Long id2 = model.getId();
			return id1 != null && id2 != null && id1.equals(id2);
		}
		return false;
	}
}
