package com.mjeanroy.springhub.dao;

import static com.google.common.collect.Iterables.isEmpty;
import static com.mjeanroy.springhub.commons.reflections.ReflectionUtils.getGenericType;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mjeanroy.springhub.models.entities.JPAEntity;

/**
 * DAO implementation.
 * This dao implementation provide shortcuts to commons methods used
 * with {@link EntityManager} instance.
 *
 * @param <PK> Type of entity id.
 * @param <T> Entity class.
 */
@SuppressWarnings("unchecked")
@Repository
public abstract class AbstractGenericDao<PK extends Serializable, T extends JPAEntity<PK>> {

	/** Parametrized class */
	protected final Class<T> type;

	/**
	 * Create new DAO.
	 * Generic type is automatically retrieved by reflection.
	 */
	protected AbstractGenericDao() {
		this.type = (Class<T>) getGenericType(getClass(), 1);
	}

	/**
	 * Access to entity Manager.
	 * This method should return entity manager to use with this
	 * dao implementation.
	 */
	protected abstract EntityManager entityManager();

	/**
	 * Get entity manager flush mode.
	 *
	 * @return Flush mode.
	 */
	public FlushModeType getFlushMode() {
		return entityManager().getFlushMode();
	}

	/**
	 * Set flush mode on entity manager.
	 *
	 * @param flushMode New flush mode.
	 */
	public void setFlushMode(FlushModeType flushMode) {
		entityManager().setFlushMode(flushMode);
	}

	/**
	 * Flush persistence context.
	 */
	public void flush() {
		entityManager().flush();
	}

	/**
	 * Clear persistence context
	 */
	public void clear() {
		entityManager().clear();
	}

	/**
	 * Check if an entity is managed by the persistence context.
	 *
	 * @param o Entity to check.
	 * @return True if entity is managed, false otherwise.
	 */
	public boolean isManaged(T o) {
		return entityManager().contains(o);
	}

	/**
	 * Persist an entity.
	 *
	 * @param o Entity to persist.
	 */
	@Transactional
	public void persist(T o) {
		entityManager().persist(o);
	}

	/**
	 * Merge an entity into the current persistence context.
	 *
	 * @param o Entity to merge.
	 * @return Merged entity.
	 */
	@Transactional
	public T merge(T o) {
		return entityManager().merge(o);
	}

	/**
	 * Remove an entity.
	 *
	 * @param o Entity to remove.
	 */
	@Transactional
	public void remove(T o) {
		entityManager().remove(o);
	}

	/**
	 * Refresh an entity from the database.
	 *
	 * @param o Entity to refresh.
	 */
	public void refresh(T o) {
		entityManager().refresh(o);
	}

	/**
	 * Detach an entity from the persistence context.
	 *
	 * @param o Entity to detach.
	 */
	public void detach(T o) {
		entityManager().detach(o);
	}

	/**
	 * Lock an entity instance that is contained in the persistence
	 * context with the specified lock mode type
	 *
	 * @param o            Entity to lock.
	 * @param lockModeType Lock type.
	 */
	public void lock(T o, LockModeType lockModeType) {
		entityManager().lock(o, lockModeType);
	}

	/**
	 * Find an entity by its primary key.
	 *
	 * @param primaryKey Primary key.
	 *
	 * @return Entity.
	 */
	public T findOne(PK primaryKey) {
		return entityManager().find(type, primaryKey);
	}

	/**
	 * Get reference to an instance, whose state may be lazily fetched.
	 *
	 * @param primaryKey Primary key.
	 *
	 * @return Reference to the entity.
	 */
	public T getOne(PK primaryKey) {
		try {
			T entity = entityManager().getReference(type, primaryKey);
			entity.getId();
			return entity;
		}
		catch (EntityNotFoundException ex) {
			return null;
		}
	}

	/**
	 * Find all entity associated to this DAO.
	 *
	 * @return All entity.
	 */
	public List<T> findAll() {
		EntityManager em = entityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(type);
		Root<T> root = query.from(type);
		query.select(root);
		return em.createQuery(query).getResultList();
	}

	/**
	 * Find all entities where id is in given collection.
	 *
	 * @return Entities.
	 */
	@SuppressWarnings("unchecked")
	public <K> List<T> findAll(Iterable<K> id) {
		if (isEmpty(id)) {
			return new ArrayList<T>();
		}

		EntityManager em = entityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(type);
		Root<T> root = query.from(type);
		query.select(root);

		// Select by id
		query.where(
				root.get("id").in(id)
		);

		return em.createQuery(query).getResultList();
	}

	/**
	 * Find all entities where attribute value is in given values.
	 * A map indexed by value is returned.
	 *
	 * @return Entities.
	 */
	@SuppressWarnings("unchecked")
	public Map<PK, T> indexById(Iterable<PK> values) {
		List<T> results = findAll(values);

		Map<PK, T> map = new HashMap<PK, T>();
		for (T result : results) {
			map.put(result.getId(), result);
		}

		return map;
	}

	/**
	 * Count all entities associated to this DAO.
	 *
	 * @return Total number of entities.
	 */
	public long count() {
		EntityManager em = entityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<T> root = query.from(type);
		query.select(builder.count(root));
		Long count = em.createQuery(query).getSingleResult();
		return count == null ? 0 : count;
	}

	/**
	 * Find list of entities for a JPQL query.
	 *
	 * @param query Query.
	 * @return All entities matching given query.
	 */
	public List<T> findAll(final CharSequence query) {
		return findAll(query, null);
	}

	/**
	 * Find list of entities for a given query and limit results to a given number.
	 *
	 * @param query Query.
	 * @param limit Maximum number of results.
	 *
	 * @return All entities matching given query.
	 */
	public List<T> findAll(final CharSequence query, final int limit) {
		return findAll(query, null, limit);
	}

	/**
	 * Find list of entities for a given query with given parameters.
	 *
	 * @param query  Query.
	 * @param params Query parameters.
	 *
	 * @return All entities matching given query.
	 */
	public List<T> findAll(CharSequence query, Map<String, ?> params) {
		return findAll(query, params, -1);
	}

	/**
	 * Find list of entities for a given query with given parameters and limit number of results to a given number.
	 *
	 * @param query  Query.
	 * @param params Query parameters.
	 * @param limit  Maximum number of results.
	 *
	 * @return All entities matching given query.
	 */
	public List<T> findAll(CharSequence query, Map<String, ?> params, int limit) {
		Query q = entityManager().createQuery(query.toString());

		if (limit > 0) {
			q.setMaxResults(limit);
		}

		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, ?> e : params.entrySet()) {
				q.setParameter(e.getKey(), e.getValue());
			}
		}

		return findAll(q);
	}

	/**
	 * Get all entities for a given query.
	 *
	 * @param query Query.
	 * @return All entities matching given query.
	 */
	public List<T> findAll(Query query) {
		return (List<T>) query.getResultList();
	}

	/**
	 * Find entity for a given query.
	 *
	 * @param query Query.
	 *
	 * @return Entity, null if no entity match given query.
	 */
	public T findOne(final CharSequence query) {
		return findOne(query, null);
	}

	/**
	 * Find entity for a given query.
	 *
	 * @param query  Query.
	 * @param params Query parameters.
	 * @return Entity, null if no entity match given query.
	 */
	public T findOne(final CharSequence query, final Map<String, ?> params) {
		final Query q = entityManager().createQuery(query.toString());
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, ?> e : params.entrySet()) {
				q.setParameter(e.getKey(), e.getValue());
			}
		}
		return findOne(q);
	}

	/**
	 * Find entity for a given query.
	 *
	 * @param query Query.
	 * @return Entity, null if no entity match given query.
	 */
	public T findOne(final Query query) {
		try {
			return (T) query.getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}

	/**
	 * Count entities matching given query.
	 *
	 * @param query Query.
	 * @return Number of entities matching given query.
	 */
	public long count(final CharSequence query) {
		return count(query, null);
	}

	/**
	 * Count entities matching given query.
	 *
	 * @param query  Query.
	 * @param params Query parameters.
	 * @return Number of entities matching given query.
	 */
	public long count(final CharSequence query, final Map<String, ?> params) {
		Query q = entityManager().createQuery(query.toString());
		if ((params != null) && (!params.isEmpty())) {
			for (Map.Entry<String, ?> e : params.entrySet()) {
				q.setParameter(e.getKey(), e.getValue());
			}
		}
		return count(q);
	}

	/**
	 * Count entities matching given query.
	 *
	 * @param query Query.
	 *
	 * @return Number of entities matching given query.
	 */
	public long count(final Query query) {
		Long count = (Long) query.getSingleResult();
		return count == null ? 0 : count;
	}
}
