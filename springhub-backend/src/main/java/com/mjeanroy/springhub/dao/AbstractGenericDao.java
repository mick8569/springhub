package com.mjeanroy.springhub.dao;

import com.mjeanroy.springhub.exceptions.ReflectionException;
import com.mjeanroy.springhub.models.entities.AbstractGenericEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mjeanroy.springhub.commons.reflections.ReflectionUtils.getGenericType;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * DAO implementation.
 *
 * @param <T> Entity class.
 */
@Repository
public abstract class AbstractGenericDao<T extends AbstractGenericEntity> {

	/** Parameterized class */
	protected Class<T> type = null;

	@SuppressWarnings("unchecked")
	public AbstractGenericDao() {
		this.type = (Class<T>) getGenericType(getClass(), 0);
	}

	/** Entity Manager */
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

	/** Flush persistence context. */
	public void flush() {
		entityManager().flush();
	}

	/** Clear persistence context */
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
	@Transactional(readOnly = false, propagation = REQUIRED)
	public void persist(T o) {
		entityManager().persist(o);
	}

	/**
	 * Merge an entity into the current persistence context.
	 *
	 * @param o Entity to merge.
	 * @return Merged entity.
	 */
	@Transactional(readOnly = false, propagation = REQUIRED)
	public T merge(T o) {
		return entityManager().merge(o);
	}

	/**
	 * Remove an entity.
	 *
	 * @param o Entity to remove.
	 */
	@Transactional(readOnly = false, propagation = REQUIRED)
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
	 * @param o Entity to lock.
	 * @param lockModeType Lock type.
	 */
	public void lock(T o, LockModeType lockModeType) {
		entityManager().lock(o, lockModeType);
	}

	/**
	 * Find an entity by its primary key.
	 *
	 * @param primaryKey Primary key.
	 * @return Entity.
	 */
	public T find(Long primaryKey) {
		return entityManager().find(type, primaryKey);
	}

	/**
	 * Get reference to an instance, whose state may be lazily fetched.
	 *
	 * @param primaryKey Primary key.
	 * @return Reference to the entity.
	 */
	public T getReference(Long primaryKey) {
		try {
			return entityManager().getReference(type, primaryKey);
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
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		String sb = "SELECT x FROM " + type.getSimpleName() + " x";
		Query query = entityManager().createQuery(sb);
		return query.getResultList();
	}

	/**
	 * Find all entities where attribute value is in given values.
	 *
	 * @return Entities.
	 */
	@SuppressWarnings("unchecked")
	public <K> List<T> findIn(Collection<K> values, String attribute) {
		StringBuilder sb = new StringBuilder()
				.append("SELECT x FROM ")
				.append(type.getSimpleName())
				.append(" x ")
				.append("WHERE x.")
				.append(attribute)
				.append(" IN (:values)");

		Query query = entityManager().createQuery(sb.toString());
		query.setParameter("values", values);
		return query.getResultList();
	}

	/**
	 * Find all entities where attribute value is in given values.
	 * A map indexed by value is returned.
	 *
	 * @return Entities.
	 */
	@SuppressWarnings("unchecked")
	public <K> Map<K, T> indexBy(Collection<K> values, String attribute) {
		Collection<T> results = findIn(values, attribute);

		Map<K, T> map = new HashMap<K, T>();

		try {
			for (T result : results) {
				Field field = result.getClass().getDeclaredField(attribute);
				field.setAccessible(true);
				K value = (K) field.get(result);
				map.put(value, result);
			}
		}
		catch (NoSuchFieldException ex) {
			throw new ReflectionException(ex);
		}
		catch (IllegalAccessException ex) {
			throw new ReflectionException(ex);
		}

		return map;
	}

	/**
	 * Find all entities where 'id' attribute value is in given values.
	 * A map indexed by 'id' value is returned.
	 *
	 * @return Entities.
	 */
	public <K> Map<K, T> indexById(Collection<K> values) {
		return indexBy(values, "id");
	}

	/**
	 * Count all entities associated to this DAO.
	 *
	 * @return Total number of entities.
	 */
	public long count() {
		String sb = "SELECT COUNT(x) FROM " + type.getSimpleName() + " x";
		Query query = entityManager().createQuery(sb);

		Long count = (Long) query.getSingleResult();
		return count == null ? 0L : count;
	}

	/**
	 * Find list of entities for a given query.
	 *
	 * @param query Query.
	 * @return All entities matching given query.
	 */
	public List<T> getEntityList(CharSequence query) {
		return getEntityList(query, null);
	}

	/**
	 * Find list of entities for a given query and limit results to a given number.
	 *
	 * @param query Query.
	 * @param limit Maximum number of results.
	 * @return All entities matching given query.
	 */
	public List<T> getEntityList(CharSequence query, int limit) {
		return getEntityList(query, null, limit);
	}

	/**
	 * Find list of entities for a given query with given parameters.
	 *
	 * @param query Query.
	 * @param params Query parameters.
	 * @return All entities matching given query.
	 */
	public List<T> getEntityList(CharSequence query, Map<String, Object> params) {
		return getEntityList(query, params, -1);
	}

	/**
	 * Find list of entities for a given query with given parameters and limit number of results to a given number.
	 *
	 * @param query Query.
	 * @param params Query parameters.
	 * @param limit Maximum number of results.
	 * @return All entities matching given query.
	 */
	public List<T> getEntityList(CharSequence query, Map<String, Object> params, int limit) {
		Query q = entityManager().createQuery(query.toString());

		if (limit > 0) {
			q.setMaxResults(limit);
		}

		if ((params != null) && (!params.isEmpty())) {
			for (Map.Entry<String, Object> e : params.entrySet()) {
				q.setParameter(e.getKey(), e.getValue());
			}
		}

		return getEntityList(q);
	}

	/**
	 * Get all entities for a given query.
	 *
	 * @param query Query.
	 * @return All entities matching given query.
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityList(Query query) {
		return (List<T>) query.getResultList();
	}

	/**
	 * Find entity for a given query.
	 *
	 * @param query Query.
	 * @return Entity, null if no entity match given query.
	 */
	public T getSingleEntity(CharSequence query) {
		return getSingleEntity(query, null);
	}

	/**
	 * Find entity for a given query.
	 *
	 * @param query Query.
	 * @param params Query parameters.
	 * @return Entity, null if no entity match given query.
	 */
	public T getSingleEntity(CharSequence query, Map<String, ?> params) {
		Query q = entityManager().createQuery(query.toString());
		if ((params != null) && (!params.isEmpty())) {
			for (Map.Entry<String, ?> e : params.entrySet()) {
				q.setParameter(e.getKey(), e.getValue());
			}
		}
		return getSingleEntity(q);
	}

	/**
	 * Find entity for a given query.
	 *
	 * @param query Query.
	 * @return Entity, null if no entity match given query.
	 */
	@SuppressWarnings("unchecked")
	public T getSingleEntity(Query query) {
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
	public long getCount(CharSequence query) {
		return getCount(query, null);
	}

	/**
	 * Count entities matching given query.
	 *
	 * @param query Query.
	 * @param params Query parameters.
	 * @return Number of entities matching given query.
	 */
	public long getCount(CharSequence query, Map<String, ?> params) {
		Query q = entityManager().createQuery(query.toString());
		if ((params != null) && (!params.isEmpty())) {
			for (Map.Entry<String, ?> e : params.entrySet()) {
				q.setParameter(e.getKey(), e.getValue());
			}
		}
		return getCount(q);
	}

	/**
	 * Count entities matching given query.
	 *
	 * @param query Query.
	 * @return Number of entities matching given query.
	 */
	public long getCount(Query query) {
		return (Long) query.getSingleResult();
	}
}
