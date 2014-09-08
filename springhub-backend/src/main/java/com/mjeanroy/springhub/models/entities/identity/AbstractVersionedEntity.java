package com.mjeanroy.springhub.models.entities.identity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import java.io.Serializable;

import com.mjeanroy.springhub.models.Model;
import com.mjeanroy.springhub.models.entities.JPAEntity;

/**
 * Versioned JPA Entity that an {@code int} version field to
 * implement optimistic locking.
 *
 * @param <PK> Generic type of id.
 */
@MappedSuperclass
public abstract class AbstractVersionedEntity<PK extends Serializable> extends AbstractEntity<PK> implements Model<PK>, JPAEntity<PK> {

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
