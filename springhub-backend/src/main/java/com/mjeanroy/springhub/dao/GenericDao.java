package com.mjeanroy.springhub.dao;

import com.mjeanroy.springhub.exceptions.NotImplementedException;
import com.mjeanroy.springhub.models.entities.JPAEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/** Generic DAO used to retrieve entities. */
public class GenericDao extends AbstractDao<JPAEntity> {

	/** Class logger */
	private static final Logger log = LoggerFactory.getLogger(GenericDao.class);

	@Override
	public JPAEntity find(Long id) {
		log.error("You have to specified entity class in generic dao");
		throw new NotImplementedException("You have to specified entity class in generic dao");
	}

	@Override
	public List<JPAEntity> findAll() {
		log.error("You have to specified entity class in generic dao");
		throw new NotImplementedException("You have to specified entity class in generic dao");
	}

	@Override
	public long count() {
		log.error("You have to specified entity class in generic dao");
		throw new NotImplementedException("You have to specified entity class in generic dao");
	}

	@Override
	public JPAEntity getReference(Long id) {
		log.error("You have to specified entity class in generic dao");
		throw new NotImplementedException("You have to specified entity class in generic dao");
	}

	/**
	 * Find item in database with its id.
	 *
	 * @param klass      Entity class of item to look for.
	 * @param primaryKey Id in database.
	 * @return Founded item or null.
	 */
	public <T extends JPAEntity> T find(Class<T> klass, Long primaryKey) {
		return entityManager.find(klass, primaryKey);
	}

	/**
	 * Get reference to an entity with its id.
	 *
	 * @param klass      Entity class of item to look for.
	 * @param primaryKey Id in database.
	 * @return Reference to founded item or null.
	 */
	public <T extends JPAEntity> T getReference(Class<T> klass, Long primaryKey) {
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
	public <T extends JPAEntity> List<T> findAll(Class<T> klass) {
		return (List<T>) entityManager.createQuery("SELECT x FROM " + klass.getSimpleName() + " x").getResultList();
	}

	/**
	 * Count all items for a specific entity.
	 *
	 * @param klass Entity class.
	 * @return Number of items in database.
	 */
	public <T extends JPAEntity> long count(Class<T> klass) {
		return (Long) entityManager.createQuery("SELECT COUNT(x) FROM " + klass.getSimpleName() + " x").getSingleResult();
	}
}
