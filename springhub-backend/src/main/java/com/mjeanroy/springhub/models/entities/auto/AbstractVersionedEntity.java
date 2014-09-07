package com.mjeanroy.springhub.models.entities.auto;

import com.mjeanroy.springhub.models.AbstractModel;
import com.mjeanroy.springhub.models.Model;
import com.mjeanroy.springhub.models.entities.JPAEntity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Versioned JPA Entity that an {@code int} version field to
 * implement optimistic locking.
 */
@MappedSuperclass
public abstract class AbstractVersionedEntity extends AbstractModel implements Model, JPAEntity {

	@Version
	protected int version;

	/**
	 * Get current version value.
	 *
	 * @return Version value.
	 */
	public int getVersion() {
		return version;
	}
}
