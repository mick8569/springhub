package com.mick8569.springhub.services;

import com.mick8569.springhub.dao.GenericDao;
import com.mick8569.springhub.models.entities.AbstractEntity;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AbstractServicesTest {

	@Mock
	private GenericDao dao;

	@InjectMocks
	private Service service;

	@Test
	public void test_find() {
		Mockito.when(dao.find(FooEntity.class, 1L)).thenReturn(new FooEntity());

		FooEntity foo = service.get(1L);
		Assertions.assertThat(foo).isNotNull();
		Mockito.verify(dao).find(FooEntity.class, 1L);
	}

	@Test
	public void test_findAll() {
		Mockito.when(dao.findAll(FooEntity.class)).thenReturn(new ArrayList<FooEntity>());

		List<FooEntity> foo = service.getAll();
		Assertions.assertThat(foo).isNotNull();
		Mockito.verify(dao).findAll(FooEntity.class);
	}

	@Test
	public void test_count() {
		Mockito.when(dao.count(FooEntity.class)).thenReturn(2L);

		long count = service.count();
		Assertions.assertThat(count).isEqualTo(2);
		Mockito.verify(dao).count(FooEntity.class);
	}

	@Test
	public void test_save() {
		FooEntity foo = new FooEntity();
		FooEntity result = service.save(foo);
		Assertions.assertThat(result).isNotNull();
		Mockito.verify(dao).persist(foo);
	}

	@Test
	public void test_delete() {
		FooEntity foo = new FooEntity();
		service.delete(foo);
		Mockito.verify(dao).remove(foo);
	}

	private static class Service extends AbstractServices<FooEntity> {

	}

	private static class FooEntity extends AbstractEntity {

	}
}
