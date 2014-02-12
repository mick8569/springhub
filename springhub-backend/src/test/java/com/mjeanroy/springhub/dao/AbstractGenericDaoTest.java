package com.mjeanroy.springhub.dao;

import com.mjeanroy.springhub.models.entities.AbstractGenericEntity;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AbstractGenericDaoTest {

	private final EntityManager entityManager = mock(EntityManager.class);

	@Test
	public void test_flush() {
		dao().flush();
		verify(entityManager).flush();
	}

	@Test
	public void test_clear() {
		dao().clear();
		verify(entityManager).clear();
	}

	@Test
	public void test_merge() {
		FooEntity foo = new FooEntity();
		dao().merge(foo);
		verify(entityManager).merge(foo);
	}

	@Test
	public void test_persist() {
		FooEntity foo = new FooEntity();
		dao().persist(foo);
		verify(entityManager).persist(foo);
	}

	@Test
	public void test_remove() {
		FooEntity foo = new FooEntity();
		dao().remove(foo);
		verify(entityManager).remove(foo);
	}

	@Test
	public void test_isManaged() {
		FooEntity foo = new FooEntity();
		dao().isManaged(foo);
		verify(entityManager).contains(foo);
	}

	@Test
	public void test_detach() {
		FooEntity foo = new FooEntity();
		dao().detach(foo);
		verify(entityManager).detach(foo);
	}

	@Test
	public void test_refresh() {
		FooEntity foo = new FooEntity();
		dao().refresh(foo);
		verify(entityManager).refresh(foo);
	}

	@Test
	public void test_find() {
		FooEntity foo = new FooEntity();
		when(entityManager.find(FooEntity.class, 1L)).thenReturn(foo);
		FooEntity result = dao().find(1L);
		assertThat(result).isNotNull().isEqualTo(foo);
		verify(entityManager).find(FooEntity.class, 1L);
	}

	@Test
	public void test_getReference() {
		FooEntity foo = new FooEntity();
		when(entityManager.getReference(FooEntity.class, 1L)).thenReturn(foo);
		FooEntity result = dao().getReference(1L);
		assertThat(result).isNotNull().isEqualTo(foo);
		verify(entityManager).getReference(FooEntity.class, 1L);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void test_getReference_notFound() {
		when(entityManager.getReference(FooEntity.class, 1L)).thenThrow(EntityNotFoundException.class);
		FooEntity result = dao().getReference(1L);
		assertThat(result).isNull();
		verify(entityManager).getReference(FooEntity.class, 1L);
	}

	@Test
	public void test_findAll() {
		List<FooEntity> entities = asList(
				new FooEntity(),
				new FooEntity()
		);

		Query query = mock(Query.class);
		when(entityManager.createQuery("SELECT x FROM FooEntity x")).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		List<FooEntity> results = dao().findAll();
		assertThat(results).isNotNull().hasSize(2).isEqualTo(entities);
		verify(entityManager).createQuery("SELECT x FROM FooEntity x");
		verify(query).getResultList();
	}

	@Test
	public void test_findIn() {
		// GIVEN
		List<FooEntity> entities = asList(
				new FooEntity(),
				new FooEntity()
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
		Query query = mock(Query.class);
		when(entityManager.createQuery("SELECT COUNT(x) FROM FooEntity x")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(2L);

		long count = dao().count();
		assertThat(count).isEqualTo(2);
		verify(entityManager).createQuery("SELECT COUNT(x) FROM FooEntity x");
		verify(query).getSingleResult();
	}

	@Test
	public void test_count_returnNull() {
		Query query = mock(Query.class);
		when(entityManager.createQuery("SELECT COUNT(x) FROM FooEntity x")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(null);

		long count = dao().count();
		assertThat(count).isEqualTo(0);
	}

	@Test
	public void test_getEntityList_singleQuery() {
		List<FooEntity> entities = asList(
				new FooEntity(),
				new FooEntity()
		);

		String str = "SELECT x FROM FooEntity x WHERE x.ID = 1";
		Query query = mock(Query.class);
		when(entityManager.createQuery(str)).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		List<FooEntity> results = dao().getEntityList(str);
		assertThat(results).isNotNull().hasSize(2).isEqualTo(entities);
		verify(entityManager).createQuery(str);		verify(query).getResultList();
		verify(query, Mockito.never()).setParameter(Mockito.anyString(), Mockito.anyObject());
		verify(query, Mockito.never()).setMaxResults(Mockito.anyInt());
	}

	@Test
	public void test_getEntityList_singleQueryWithLimit() {
		List<FooEntity> entities = asList(
				new FooEntity(),
				new FooEntity()
		);

		String str = "SELECT x FROM FooEntity x WHERE x.ID = 1";
		Query query = mock(Query.class);
		when(entityManager.createQuery(str)).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		List<FooEntity> results = dao().getEntityList(str, 10);
		assertThat(results).isNotNull().hasSize(2).isEqualTo(entities);
		verify(entityManager).createQuery(str);
		verify(query).getResultList();
		verify(query, Mockito.never()).setParameter(Mockito.anyString(), Mockito.anyObject());
		verify(query).setMaxResults(10);
	}

	@Test
	public void test_getEntityList_singleQueryWithParameters() {
		List<FooEntity> entities = asList(
				new FooEntity(),
				new FooEntity()
		);

		String str = "SELECT x FROM FooEntity x WHERE x.ID = :id";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", 1L);

		Query query = mock(Query.class);
		when(entityManager.createQuery(str)).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		List<FooEntity> results = dao().getEntityList(str, parameters);
		assertThat(results).isNotNull().hasSize(2).isEqualTo(entities);
		verify(entityManager).createQuery(str);
		verify(query).getResultList();
		verify(query).setParameter("id", 1L);
		verify(query, Mockito.never()).setMaxResults(Mockito.anyInt());
	}

	@Test
	public void test_getEntityList_singleQueryWithLimitAndParameters() {
		List<FooEntity> entities = asList(
				new FooEntity(),
				new FooEntity()
		);

		String str = "SELECT x FROM FooEntity x WHERE x.ID = :id";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", 1L);

		Query query = mock(Query.class);
		when(entityManager.createQuery(str)).thenReturn(query);
		when(query.getResultList()).thenReturn(entities);

		List<FooEntity> results = dao().getEntityList(str, parameters, 10);
		assertThat(results).isNotNull().hasSize(2).isEqualTo(entities);
		verify(entityManager).createQuery(str);
		verify(query).getResultList();
		verify(query).setParameter("id", 1L);
		verify(query).setMaxResults(10);
	}

	@Test
	public void test_getSingleEntity() {
		FooEntity foo = new FooEntity();
		String str = "SELECT x FROM FooEntity x WHERE x.ID = 1";

		Query query = mock(Query.class);
		when(entityManager.createQuery(str)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(foo);

		FooEntity result = dao().getSingleEntity(str);
		assertThat(result).isNotNull().isEqualTo(foo);
		verify(entityManager).createQuery(str);
		verify(query).getSingleResult();
		verify(query, Mockito.never()).setParameter(Mockito.anyString(), Mockito.anyObject());
	}

	@Test
	public void test_getSingleEntity_withParameters() {
		FooEntity foo = new FooEntity();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", 1L);
		String str = "SELECT x FROM FooEntity x WHERE x.ID = :id";

		Query query = mock(Query.class);
		when(entityManager.createQuery(str)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(foo);

		FooEntity result = dao().getSingleEntity(str, parameters);
		assertThat(result).isNotNull().isEqualTo(foo);
		verify(entityManager).createQuery(str);
		verify(query).getSingleResult();
		verify(query).setParameter("id", 1L);
	}

	@Test
	public void test_getCount() {
		String str = "SELECT COUNT(x) FROM FooEntity x WHERE x.ID = 1";

		Query query = mock(Query.class);
		when(entityManager.createQuery(str)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(2L);

		long result = dao().getCount(str);
		assertThat(result).isEqualTo(2);
		verify(entityManager).createQuery(str);
		verify(query).getSingleResult();
		verify(query, Mockito.never()).setParameter(Mockito.anyString(), Mockito.anyObject());
	}

	@Test
	public void test_getCount_withParameter() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", 1L);
		String str = "SELECT COUNT(x) FROM FooEntity x WHERE x.ID = :id";

		Query query = mock(Query.class);
		when(entityManager.createQuery(str)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(2L);

		long result = dao().getCount(str, parameters);
		assertThat(result).isEqualTo(2);
		verify(entityManager).createQuery(str);
		verify(query).getSingleResult();
		verify(query).setParameter("id", 1L);
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
		String str = "SELECT x FROM FooEntity x WHERE x.ID = 1";

		Query query = mock(Query.class);
		when(entityManager.createQuery(str)).thenReturn(query);
		when(query.getSingleResult()).thenThrow(NoResultException.class);

		FooEntity result = dao().getSingleEntity(str);
		assertThat(result).isNull();
		verify(entityManager).createQuery(str);
		verify(query).getSingleResult();
	}

	private static class FooEntity extends AbstractGenericEntity {
		@Override
		public Long entityId() {
			return 1L;
		}
	}
}
