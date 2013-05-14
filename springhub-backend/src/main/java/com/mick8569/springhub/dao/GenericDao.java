package com.mick8569.springhub.dao;

import com.mick8569.springhub.exceptions.NotImplementedException;
import com.mick8569.springhub.models.entities.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import javax.persistence.EntityNotFoundException;

/** Generic DAO used to retrieve entities. */
public class GenericDao extends AbstractDao<AbstractEntity> {

	/** Class logger */
	private static final Logger LOG = LoggerFactory.getLogger(GenericDao.class);

	@Override
	public AbstractEntity find(Long id) {
		LOG.error("You have to specified entity class in generic dao");
		throw new NotImplementedException("You have to specified entity class in generic dao");
	}

	@Override
	public List<AbstractEntity> findAll() {
		LOG.error("You have to specified entity class in generic dao");
		throw new NotImplementedException("You have to specified entity class in generic dao");
	}

	@Override
	public long count() {
		LOG.error("You have to specified entity class in generic dao");
		throw new NotImplementedException("You have to specified entity class in generic dao");
	}

	@Override
	public AbstractEntity getReference(Long id) {
		LOG.error("You have to specified entity class in generic dao");
		throw new NotImplementedException("You have to specified entity class in generic dao");
	}

	/**
	 * Find item in database with its id.
	 *
	 * @param klass      Entity class of item to look for.
	 * @param primaryKey Id in database.
	 * @return Founded item or null.
	 */
	public <T extends AbstractEntity> T find(Class<T> klass, Long primaryKey) {
		return entityManager.find(klass, primaryKey);
	}

	/**
	 * Get reference to an entity with its id.
	 *
	 * @param klass      Entity class of item to look for.
	 * @param primaryKey Id in database.
	 * @return Reference to founded item or null.
	 */
	public <T extends AbstractEntity> T getReference(Class<T> klass, Long primaryKey) {
		try {
			return entityManager.getReference(klass, primaryKey);
		}
		catch (EntityNotFoundException ex) {
			return null;
		}
	}

	/**
	 * Find all items for a specific entity.
	 *
	 * @param klass Entity class.
	 * @return All items in database.
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractEntity> List<T> findAll(Class<T> klass) {
		return (List<T>) entityManager.createQuery("SELECT x FROM " + klass.getSimpleName() + " x").getResultList();
	}

	/**
	 * Count all items for a specific entity.
	 *
	 * @param klass Entity class.
	 * @return Number of items in database.
	 */
	public <T extends AbstractEntity> long count(Class<T> klass) {
		return (Long) entityManager.createQuery("SELECT COUNT(x) FROM " + klass.getSimpleName() + " x").getSingleResult();
	}
}
