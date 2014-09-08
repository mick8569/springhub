package com.mjeanroy.springhub.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mjeanroy.springhub.models.entities.JPAEntity;

/** Generic DAO used to retrieve entities. */
public class GenericDao {

	@PersistenceContext
	protected EntityManager entityManager;

	/**
	 * Persist entity using entity manager.
	 *
	 * @param entity Entity to persist.
	 * @param <T> Type of entity.
	 * @return Persisted entity.
	 */
	public <T extends JPAEntity> T persist(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	/**
	 * Remove entity using entity manager.
	 *
	 * @param entity Entity to remove.
	 * @param <T> Type of entity.
	 */
	public <T extends JPAEntity> void remove(T entity) {
		entityManager.remove(entity);
	}

	/**
	 * Retrieve list of entities by id and index them in map using id value as key.
	 *
	 * @param klass Type of entities to look for.
	 * @param ids List of id.
	 * @param <PK> Type of id.
	 * @param <T> Type of entities.
	 * @return Map of entities.
	 */
	@SuppressWarnings("unchecked")
	public <PK extends Serializable, T extends JPAEntity<PK>> Map<PK, T> indexById(Class<T> klass, Collection<PK> ids) {
		List<T> entities = (List<T>) entityManager.createQuery("SELECT x FROM " + klass.getSimpleName() + " x").getResultList();
		Map<PK, T> map = new HashMap<PK, T>();
		for (T entity : entities) {
			PK id = entity.getId();
			map.put(id, entity);
		}
		return map;
	}

	/**
	 * Find item in database with its id.
	 *
	 * @param klass Entity class of item to look for.
	 * @param primaryKey Id in database.
	 * @return Founded item or null.
	 */
	public <PK extends Serializable, T extends JPAEntity<PK>> T find(Class<T> klass, PK primaryKey) {
		return entityManager.find(klass, primaryKey);
	}

	/**
	 * Get reference to an entity with its id.
	 *
	 * @param klass Entity class of item to look for.
	 * @param primaryKey Id in database.
	 * @return Reference to founded item or null.
	 */
	public <PK extends Serializable, T extends JPAEntity<PK>> T getReference(Class<T> klass, PK primaryKey) {
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
