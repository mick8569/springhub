package com.mjeanroy.springhub.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

import com.mjeanroy.springhub.models.entities.JPAEntity;

/**
 * Abstract DAO using default configuration :
 * <ul>
 * <li>Primary key if a Long</li>
 * <li>Entity Manager is retrieved from persistence context</li>
 * </ul>
 *
 * @param <PK> Type of entity id.
 * @param <T> Entity Class used by this dao.
 */
public abstract class AbstractDao<PK extends Serializable, T extends JPAEntity<PK>> extends AbstractGenericDao<PK, T> {

	/** Entity manager */
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}

}
