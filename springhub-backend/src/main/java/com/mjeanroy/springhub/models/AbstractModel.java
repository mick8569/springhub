package com.mjeanroy.springhub.models;

import static java.lang.String.format;

import java.io.Serializable;
import java.util.UUID;

/**
 * Partial implementation of model object.
 * This abstraction implements:
 * - {@code toString} method.
 * - Both {@code equals} and {@code hashCode} methods.
 * - {@code isNew} method.
 *
 * @param <PK> Generic type of id.
 */
public abstract class AbstractModel<PK extends Serializable> implements Model<PK> {

	/**
	 * Hash code value should not changed when it has been computed.
	 * Since basic implementation use id to compute hash code, we should use
	 * an alternative when id is null and is set after hash code has been computed.
	 *
	 * This uuid will be initialized if hash code value is computed when the value returned
	 * by {#getId()} is null. When it has been computed, hashCode will always use this uuid
	 * even if id is not null.
	 *
	 * Note that if hashCode value can changed, we could have strange behavior with map
	 * and set collections (since it uses internally hashCode).
	 */
	private Object uuid;

	@Override
	public boolean isNew() {
		return getId() == null;
	}

	@Override
	public String toString() {
		return format("%s{id=%s}", getClass().getSimpleName(), getId());
	}

	private Object uuid() {
		if (uuid == null) {
			uuid = UUID.randomUUID();
		}
		return uuid;
	}

	@Override
	public int hashCode() {
		PK id = getId();
		if (uuid != null) {
			return uuid.hashCode();
		}
		if (id == null) {
			uuid = uuid();
			return uuid.hashCode();
		}
		return id.hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o != null && o.getClass().equals(getClass())) {
			AbstractModel<PK> model = (AbstractModel<PK>) o;
			PK id1 = getId();
			PK id2 = model.getId();

			if (id1 != null && id2 != null && id1.equals(id2)) {
				// Both id are equals
				return true;
			}

			if (id1 != null && id2 == null || id1 == null && id2 != null) {
				// One id is null but the second id is not
				return false;
			}

			// Both id are null, we can assume that objects are not equals.
			return false;
		}
		return false;
	}
}
