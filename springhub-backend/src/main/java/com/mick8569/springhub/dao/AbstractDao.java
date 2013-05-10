package com.mick8569.springhub.dao;

import com.mick8569.springhub.models.entities.AbstractEntity;

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
public abstract class AbstractDao<T extends AbstractEntity> extends AbstractGenericDao<T> {

	/** Entity manager */
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}

}
