package com.mick8569.springhub.dao;

import com.mick8569.springhub.exceptions.NotImplementedException;
import com.mick8569.springhub.models.entities.AbstractEntity;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GenericDaoTest {

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private GenericDao dao;

	@Test(expected = NotImplementedException.class)
	public void test_find() {
		dao.find(1L);
	}

	@Test(expected = NotImplementedException.class)
	public void test_findAll() {
		dao.findAll();
	}

	@Test(expected = NotImplementedException.class)
	public void test_count() {
		dao.count();
	}

	@Test
	public void test_find_class() {
		Mockito.when(entityManager.find(FooEntity.class, 1L)).thenReturn(new FooEntity());
		FooEntity result = dao.find(FooEntity.class, 1L);
		Assertions.assertThat(result).isNotNull();
		Mockito.verify(entityManager).find(FooEntity.class, 1L);
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
