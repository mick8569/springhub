package com.mjeanroy.springhub.dao;

import com.mjeanroy.springhub.models.entities.JPAEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Abstract DAO using default configuration :
 * <ul>
 * <li>Primary key if a Long</li>
 * <li>Entity Manager is retrieved from persistence context</li>
 * </ul>
 *
 * @param <T> Entity Class used by this dao.
 */
public abstract class AbstractDao<T extends JPAEntity> extends AbstractGenericDao<T> {

	/** Entity manager */
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}

}
