package com.mjeanroy.springhub.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mjeanroy.springhub.models.entities.identity.AbstractEntity;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class GenericDaoTest {

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private GenericDao dao;

	@Test
	public void test_find_class() {
		Mockito.when(entityManager.find(FooEntity.class, 1L)).thenReturn(new FooEntity());
		FooEntity result = dao.find(FooEntity.class, 1L);
		Assertions.assertThat(result).isNotNull();
		Mockito.verify(entityManager).find(FooEntity.class, 1L);
	}

	@Test
	public void test_getReference_class() {
		Mockito.when(entityManager.getReference(FooEntity.class, 1L)).thenReturn(new FooEntity());
		FooEntity result = dao.getReference(FooEntity.class, 1L);
		Assertions.assertThat(result).isNotNull();
		Mockito.verify(entityManager).getReference(FooEntity.class, 1L);
	}

	@Test
	public void test_getReference_exception_class() {
		Mockito.when(entityManager.getReference(FooEntity.class, 1L)).thenThrow(EntityNotFoundException.class);
		FooEntity result = dao.getReference(FooEntity.class, 1L);
		Assertions.assertThat(result).isNull();
		Mockito.verify(entityManager).getReference(FooEntity.class, 1L);
	}

	@Test
	public void test_findAll_class() {
		String str = "SELECT x FROM FooEntity x";

		Query query = Mockito.mock(Query.class);
		Mockito.when(entityManager.createQuery(str)).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(new ArrayList<FooEntity>());

		List<FooEntity> result = dao.findAll(FooEntity.class);
		Assertions.assertThat(result).isNotNull();
		Mockito.verify(entityManager).createQuery(str);
		Mockito.verify(query).getResultList();
	}

	@Test
	public void test_count_class() {
		String str = "SELECT COUNT(x) FROM FooEntity x";

		Query query = Mockito.mock(Query.class);
		Mockito.when(entityManager.createQuery(str)).thenReturn(query);
		Mockito.when(query.getSingleResult()).thenReturn(2L);

		long result = dao.count(FooEntity.class);
		Assertions.assertThat(result).isEqualTo(2);
		Mockito.verify(entityManager).createQuery(str);
		Mockito.verify(query).getSingleResult();
	}

	private static class FooEntity extends AbstractEntity {

	}
}
