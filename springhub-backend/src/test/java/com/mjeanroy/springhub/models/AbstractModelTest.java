package com.mjeanroy.springhub.models;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mjeanroy.springhub.utils.FooModel;

public class AbstractModelTest {

	@Test
	public void isNew_with_id_null_should_return_true() {
		FooModel model = new FooModel();
		assertThat(model.isNew()).isTrue();
	}

	@Test
	public void isNew_with_id_not_null_should_return_false() {
		FooModel model = new FooModel(1L);
		assertThat(model.isNew()).isFalse();
	}

	@Test
	public void hashCode_should_not_changed() {
		FooModel model = new FooModel();

		int oldHashCode = model.hashCode();
		assertThat(model.getId()).isNull();
		assertThat(oldHashCode).isNotNull();

		model.setId(1L);
		int newHashCode = model.hashCode();
		assertThat(model.getId()).isNotNull();
		assertThat(newHashCode).isNotNull().isEqualTo(oldHashCode);
	}

	@Test
	public void models_should_be_found_in_map() {
		String foo = "foo";
		FooModel model = new FooModel();

		// Put model in map without id
		Map<FooModel, String> map = new HashMap<FooModel, String>();
		map.put(model, foo);

		// Set id in map and try to retrieve model
		model.setId(1L);
		String putValue = map.get(model);
		assertThat(putValue).isNotNull().isEqualTo(foo);
	}

	@Test
	public void equals_with_null_id_should_be_false() {
		FooModel model1 = new FooModel();
		FooModel model2 = new FooModel();
		assertThat(model1.equals(model2)).isFalse();
	}

	@Test
	public void equals_with_same_id_should_be_true() {
		FooModel model1 = new FooModel(1L);
		FooModel model2 = new FooModel(1L);
		assertThat(model1.equals(model2)).isTrue();
	}

	@Test
	public void equals_with_different_id_should_be_false() {
		FooModel model1 = new FooModel(1L);
		FooModel model2 = new FooModel(2L);
		assertThat(model1.equals(model2)).isFalse();
	}

	@Test
	public void equals_with_id1_null_should_be_false() {
		FooModel model1 = new FooModel();
		FooModel model2 = new FooModel(2L);
		assertThat(model1.equals(model2)).isFalse();
	}

	@Test
	public void equals_with_id2_null_should_be_false() {
		FooModel model1 = new FooModel(1L);
		FooModel model2 = new FooModel();
		assertThat(model1.equals(model2)).isFalse();
	}
}
