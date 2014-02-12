package com.mjeanroy.springhub.mappers;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.fest.assertions.api.Assertions.assertThat;

public class AbstractMapperTest {

	private FooMapper fooMapper;

	@Before
	public void setUp() {
		fooMapper = new FooMapper();
		fooMapper.mapper = new DozerBeanMapper();
	}

	@Test
	public void getDto_should_build_dto_from_model() {
		// GIVEN
		long id = 1L;
		String name = "foo";
		FooModel model = new FooModel();
		model.setId(id);
		model.setName(name);

		// WHEN
		FooDto dto = fooMapper.getDto(model);

		// THEN
		assertThat(dto.getId()).isEqualTo(id);
		assertThat(dto.getName()).isEqualTo(name);
	}

	@Test
	public void getDtos_should_build_list_of_dto_from_models() {
		// GIVEN
		long id1 = 1L;
		String name1 = "foo";

		FooModel model1 = new FooModel();
		model1.setId(id1);
		model1.setName(name1);

		long id2 = 1L;
		String name2 = "foo";

		FooModel model2 = new FooModel();
		model2.setId(id2);
		model2.setName(name2);

		Collection<FooModel> models = asList(model1, model2);

		// WHEN
		Collection<FooDto> dtos = fooMapper.getDtos(models);

		// THEN
		assertThat(dtos).isNotNull().isNotEmpty().hasSameSizeAs(models);

		List<FooDto> lst = asList(dtos.toArray(new FooDto[dtos.size()]));
		assertThat(lst.get(0).getId()).isEqualTo(id1);
		assertThat(lst.get(0).getName()).isEqualTo(name1);
		assertThat(lst.get(1).getId()).isEqualTo(id2);
		assertThat(lst.get(1).getName()).isEqualTo(name2);
	}

	@Test
	public void getDtos_should_return_empty_list_if_models_is_null() {
		// GIVEN
		Collection<FooModel> models = null;

		// WHEN
		Collection<FooDto> dtos = fooMapper.getDtos(models);

		// THEN
		assertThat(dtos).isNotNull().isEmpty();
	}

	@Test
	public void getDtos_should_return_empty_list_if_models_is_empty() {
		// GIVEN
		Collection<FooModel> models = emptyList();

		// WHEN
		Collection<FooDto> dtos = fooMapper.getDtos(models);

		// THEN
		assertThat(dtos).isNotNull().isEmpty();
	}

	@Test
	public void getDto_should_return_null_if_model_is_null() {
		// GIVEN
		FooModel model = null;

		// WHEN
		FooDto dto = fooMapper.getDto(model);

		// THEN
		assertThat(dto).isNull();
	}
}
