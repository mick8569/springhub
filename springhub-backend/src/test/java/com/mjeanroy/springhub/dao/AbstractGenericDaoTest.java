package com.mjeanroy.springhub.dao;

import com.mjeanroy.springhub.models.entities.AbstractGenericEntity;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AbstractGenericDaoTest {

	private final EntityManager entityManager = mock(EntityManager.class);

	@Test
	public void test_flush() {
		// WHEN
		dao().flush();

		// THEN
		verify(entityManager).flush();
	}

	@Test
	public void test_clear() {
		// WHEN
		dao().clear();

		// THEN
		verify(entityManager).clear();
	}

	@Test
	public void test_merge() {
		// GIVEN
		FooEntity foo = mock(FooEntity.class);

		FooEntity mergedFoo = mock(FooEntity.class);
		when(entityManager.merge(foo)).thenReturn(mergedFoo);

		// WHEN
		FooEntity result = dao().merge(foo);

		// THEN
		assertThat(result).isEqualTo(mergedFoo);
		verify(entityManager).merge(foo);
	}

	@Test
	public void test_persist() {
		// GIVEN
		FooEntity foo = mock(FooEntity.class);

		// WHEN
		dao().persist(foo);

		// THEN
		verify(entityManager).persist(foo);
	}

	@Test
	public void test_remove() {
		// GIVEN
		FooEntity foo = mock(FooEntity.class);

		// WHEN
		dao().remove(foo);

		// THEN
		verify(entityManager).remove(foo);
	}

	@Test
	public void test_isManaged() {
		// GIVEN
		FooEntity foo = mock(FooEntity.class);

		boolean contains = true;
		when(entityManager.contains(foo)).thenReturn(contains);

		// WHEN
		boolean isManaged = dao().isManaged(foo);

		// THEN
		assertThat(isManaged).isEqualTo(contains);
		verify(entityManager).contains(foo);
	}

	@Test
	public void test_detach() {
		// GIVEN
		FooEntity foo = mock(FooEntity.class);

		// WHEN
		dao().detach(foo);

		// THEN
		verify(entityManager).detach(foo);
	}

	@Test
	public void test_refresh() {
		// GIVEN
		FooEntity foo = mock(FooEntity.class);

		// WHEN
		dao().refresh(foo);

		// THEN
		verify(entityManager).refresh(foo);
	}

	@Test
	public void test_find() {
		// GIVEN
		long id = 1L;
		FooEntity foo = mock(FooEntity.class);
		when(entityManager.find(FooEntity.class, id)).thenReturn(foo);

		// WHEN
		FooEntity result = dao().find(id);

		// THEN
		assertThat(result).isNotNull().isEqualTo(foo);
		verify(entityManager).find(FooEntity.class, id);
	}

	@Test
	public void test_getReference() {
		// GIVEN
		long id = 1L;
		FooEntity foo = mock(FooEntity.class);
		when(entityManager.getReference(FooEntity.class, id)).thenReturn(foo);

		// WHEN
		FooEntity result = dao().getReference(id);

		// THEN
		assertThat(result).isNotNull().isEqualTo(foo);
		verify(entityManager).getReference(FooEntity.class, id);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void test_getReference_notFound() {
		// GIVEN
		long id = 1L;
		when(entityManager.getReference(FooEntity.class, id)).thenThrow(EntityNotFoundException.class);

		// WHEN
		FooEntity result = dao().getReference(id);

		// THEN
		assertThat(result).isNull();
		verify(entityManager).getReference(FooEntity.class, id);
	}

	@Test
	public void test_findAll() {
		// GIVEN
		List<FooEntity> entities = asList(
				mock(FooEntity.class),
				mock(FooEntity.class)
		);

		Query query = mock(Query.class);
		when(entityManager.createQuery("SELECT x FROM FooEntity x")).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		// WHEN
		List<FooEntity> results = dao().findAll();

		// THEN
		assertThat(results).isNotNull().hasSameSizeAs(entities).isEqualTo(entities);
		verify(entityManager).createQuery("SELECT x FROM FooEntity x");
		verify(query).getResultList();
	}

	@Test
	public void test_indexBy() {
		// GIVEN
		long idFoo1 = 1L;
		long idFoo2 = 2L;
		long idFoo5 = 5L;

		FooEntity foo1 = new FooEntity(idFoo1);
		FooEntity foo2 = new FooEntity(idFoo2);
		FooEntity foo5 = new FooEntity(idFoo5);
		List<FooEntity> entities = asList(foo1, foo2, foo5);

		List<Long> ids = asList(idFoo1, idFoo2, idFoo5);

		Query query = mock(Query.class);

		String sql = "SELECT x FROM FooEntity x WHERE x.id IN (:values)";
		when(entityManager.createQuery(sql)).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		// WHEN
		Map<Long, FooEntity> results = dao().indexBy(ids, "id");

		// THEN
		assertThat(results).isNotNull().hasSameSizeAs(entities);
		assertThat(results.get(idFoo1)).isNotNull().isEqualTo(foo1);
		assertThat(results.get(idFoo2)).isNotNull().isEqualTo(foo2);
		assertThat(results.get(idFoo5)).isNotNull().isEqualTo(foo5);
		verify(entityManager).createQuery(sql);
		verify(query).getResultList();
		verify(query).setParameter("values", ids);
	}

	@Test
	public void test_indexById() {
		// GIVEN
		long idFoo1 = 1L;
		long idFoo2 = 2L;
		long idFoo5 = 5L;

		FooEntity foo1 = new FooEntity(idFoo1);
		FooEntity foo2 = new FooEntity(idFoo2);
		FooEntity foo5 = new FooEntity(idFoo5);
		List<FooEntity> entities = asList(foo1, foo2, foo5);

		List<Long> ids = asList(idFoo1, idFoo2, idFoo5);

		Query query = mock(Query.class);

		String sql = "SELECT x FROM FooEntity x WHERE x.id IN (:values)";
		when(entityManager.createQuery(sql)).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		// WHEN
		Map<Long, FooEntity> results = dao().indexById(ids);

		// THEN
		assertThat(results).isNotNull().hasSameSizeAs(entities);
		assertThat(results.get(idFoo1)).isNotNull().isEqualTo(foo1);
		assertThat(results.get(idFoo2)).isNotNull().isEqualTo(foo2);
		assertThat(results.get(idFoo5)).isNotNull().isEqualTo(foo5);
		verify(entityManager).createQuery(sql);
		verify(query).getResultList();
		verify(query).setParameter("values", ids);
	}

	@Test
	public void test_findIn() {
		// GIVEN
		List<FooEntity> entities = asList(
				mock(FooEntity.class),
				mock(FooEntity.class)
		);

		List<Long> ids = asList(1L, 2L, 5L);

		Query query = mock(Query.class);

		String sql = "SELECT x FROM FooEntity x WHERE x.id IN (:values)";
		when(entityManager.createQuery(sql)).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		// WHEN
		List<FooEntity> results = dao().findIn(ids, "id");

		// THEN
		assertThat(results).isNotNull().hasSameSizeAs(entities).isEqualTo(entities);
		verify(entityManager).createQuery(sql);
		verify(query).getResultList();
		verify(query).setParameter("values", ids);
	}

	@Test
	public void test_count() {
		// GIVEN
		Query query = mock(Query.class);
		when(entityManager.createQuery("SELECT COUNT(x) FROM FooEntity x")).thenReturn(query);

		long countResult = 2L;
		when(query.getSingleResult()).thenReturn(countResult);

		// WHEN
		long count = dao().count();

		// THEN
		assertThat(count).isEqualTo(countResult);
		verify(entityManager).createQuery("SELECT COUNT(x) FROM FooEntity x");
		verify(query).getSingleResult();
	}

	@Test
	public void test_count_returnNull() {
		// GIVEN
		Query query = mock(Query.class);
		when(entityManager.createQuery("SELECT COUNT(x) FROM FooEntity x")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(null);

		// WHEN
		long count = dao().count();

		// THEN
		assertThat(count).isZero();
	}

	@Test
	public void test_getEntityList_singleQuery() {
		// GIVEN
		List<FooEntity> entities = asList(
				mock(FooEntity.class),
				mock(FooEntity.class)
		);

		String jpql = "SELECT x FROM FooEntity x WHERE x.ID = 1";
		Query query = mock(Query.class);
		when(entityManager.createQuery(jpql)).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		// WHEN
		List<FooEntity> results = dao().getEntityList(jpql);

		// THEN
		assertThat(results).isNotNull().hasSameSizeAs(entities).isEqualTo(entities);
		verify(entityManager).createQuery(jpql);
		verify(query).getResultList();
		verify(query, never()).setParameter(anyString(), anyObject());
		verify(query, never()).setMaxResults(anyInt());
	}

	@Test
	public void test_getEntityList_singleQueryWithLimit() {
		// GIVEN
		List<FooEntity> entities = asList(
				mock(FooEntity.class),
				mock(FooEntity.class)
		);

		String jpql = "SELECT x FROM FooEntity x WHERE x.ID = 1";
		Query query = mock(Query.class);
		when(entityManager.createQuery(jpql)).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		int limit = 10;

		// WHEN
		List<FooEntity> results = dao().getEntityList(jpql, limit);

		// THEN
		assertThat(results).isNotNull().hasSameSizeAs(entities).isEqualTo(entities);
		verify(entityManager).createQuery(jpql);
		verify(query).getResultList();
		verify(query, never()).setParameter(anyString(), anyObject());
		verify(query).setMaxResults(limit);
	}

	@Test
	public void test_getEntityList_singleQueryWithParameters() {
		// GIVEN
		List<FooEntity> entities = asList(
				mock(FooEntity.class),
				mock(FooEntity.class)
		);

		String jpql = "SELECT x FROM FooEntity x WHERE x.ID = :id";

		String parameterKey = "id";
		long parameterValue = 1L;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(parameterKey, parameterValue);

		Query query = mock(Query.class);
		when(entityManager.createQuery(jpql)).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		// WHEN
		List<FooEntity> results = dao().getEntityList(jpql, parameters);

		// THEN
		assertThat(results).isNotNull().hasSameSizeAs(entities).isEqualTo(entities);
		verify(entityManager).createQuery(jpql);
		verify(query).getResultList();
		verify(query).setParameter(parameterKey, parameterValue);
		verify(query, never()).setMaxResults(anyInt());
	}

	@Test
	public void test_getEntityList_singleQueryWithLimitAndParameters() {
		// GIVEN
		List<FooEntity> entities = asList(
				mock(FooEntity.class),
				mock(FooEntity.class)
		);

		String jpql = "SELECT x FROM FooEntity x WHERE x.ID = :id";

		String parameterKey = "id";
		long parameterValue = 1L;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(parameterKey, parameterValue);

		Query query = mock(Query.class);
		when(entityManager.createQuery(jpql)).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		int limit = 10;

		// WHEN
		List<FooEntity> results = dao().getEntityList(jpql, parameters, limit);

		// THEN
		assertThat(results).isNotNull().hasSameSizeAs(entities).isEqualTo(entities);
		verify(entityManager).createQuery(jpql);
		verify(query).getResultList();
		verify(query).setParameter(parameterKey, parameterValue);
		verify(query).setMaxResults(limit);
	}

	@Test
	public void test_getSingleEntity() {
		// GIVEN
		FooEntity foo = mock(FooEntity.class);

		String jpql = "SELECT x FROM FooEntity x WHERE x.ID = 1";

		Query query = mock(Query.class);
		when(entityManager.createQuery(jpql)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(foo);

		// WHEN
		FooEntity result = dao().getSingleEntity(jpql);

		// THEN
		assertThat(result).isNotNull().isEqualTo(foo);
		verify(entityManager).createQuery(jpql);
		verify(query).getSingleResult();
		verify(query, never()).setParameter(anyString(), anyObject());
	}

	@Test
	public void test_getSingleEntity_withParameters() {
		// GIVEN
		FooEntity foo = mock(FooEntity.class);

		String parameterKey = "id";
		long parameterValue = 1L;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(parameterKey, parameterValue);

		String jpql = "SELECT x FROM FooEntity x WHERE x.ID = :id";

		Query query = mock(Query.class);
		when(entityManager.createQuery(jpql)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(foo);

		// WHEN
		FooEntity result = dao().getSingleEntity(jpql, parameters);

		// THEN
		assertThat(result).isNotNull().isEqualTo(foo);
		verify(entityManager).createQuery(jpql);
		verify(query).getSingleResult();
		verify(query).setParameter(parameterKey, parameterValue);
	}

	@Test
	public void test_getCount() {
		// GIVEN
		String jpql = "SELECT COUNT(x) FROM FooEntity x WHERE x.ID = 1";

		Query query = mock(Query.class);
		when(entityManager.createQuery(jpql)).thenReturn(query);

		long countValue = 2L;
		when(query.getSingleResult()).thenReturn(countValue);

		// WHEN
		long result = dao().getCount(jpql);

		// THEN
		assertThat(result).isEqualTo(countValue);
		verify(entityManager).createQuery(jpql);
		verify(query).getSingleResult();
		verify(query, never()).setParameter(anyString(), anyObject());
	}

	@Test
	public void test_getCount_withParameter() {
		// GIVEN
		String parameterKey = "id";
		long parameterValue = 1L;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(parameterKey, parameterValue);

		String jpql = "SELECT COUNT(x) FROM FooEntity x WHERE x.ID = :id";

		Query query = mock(Query.class);
		when(entityManager.createQuery(jpql)).thenReturn(query);

		long countValue = 2L;
		when(query.getSingleResult()).thenReturn(countValue);

		// WHEN
		long result = dao().getCount(jpql, parameters);

		// THEN
		assertThat(result).isEqualTo(countValue);
		verify(entityManager).createQuery(jpql);
		verify(query).getSingleResult();
		verify(query).setParameter(parameterKey, parameterValue);
	}

	private AbstractGenericDao<FooEntity> dao() {
		return new AbstractGenericDao<FooEntity>() {
			@Override
			protected EntityManager entityManager() {
				return entityManager;
			}
		};
	}

	@Test
	@SuppressWarnings("unchecked")
	public void test_getSingleEntity_noResultException() {
		// GIVEN
		String jpql = "SELECT x FROM FooEntity x WHERE x.ID = 1";

		Query query = mock(Query.class);
		when(entityManager.createQuery(jpql)).thenReturn(query);
		when(query.getSingleResult()).thenThrow(NoResultException.class);

		// WHEN
		FooEntity result = dao().getSingleEntity(jpql);

		// THEN
		assertThat(result).isNull();
		verify(entityManager).createQuery(jpql);
		verify(query).getSingleResult();
	}

	private static class FooEntity extends AbstractGenericEntity {
		private Long id;

		public FooEntity() {
		}

		public FooEntity(Long id) {
			this.id = id;
		}

		public Long getId() {
			return id;
		}

		@Override
		public Long entityId() {
			return 1L;
		}
	}
}
