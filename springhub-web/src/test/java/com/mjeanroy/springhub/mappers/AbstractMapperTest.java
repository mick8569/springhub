package com.mjeanroy.springhub.mappers;

import com.mjeanroy.springhub.dao.GenericDao;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class AbstractMapperTest {

	private FooMapper fooMapper;

	@Before
	public void setUp() {
		fooMapper = new FooMapper();
		fooMapper.mapper = new DozerBeanMapper();
		fooMapper.genericDao = mock(GenericDao.class);

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

	@Test
	public void getEntity_should_return_null_if_dto_is_null() {
		// GIVEN
		FooDto dto = null;

		// WHEN
		FooModel entity = fooMapper.getEntity(dto);

		// THEN
		assertThat(entity).isNull();
	}

	@Test
	public void getEntity_should_return_new_entity_corresponding_to_dto_is_null() {
		// GIVEN
		String name = "foo";
		FooDto dto = new FooDto();
		dto.setName(name);

		// WHEN
		FooModel entity = fooMapper.getEntity(dto);

		// THEN
		assertThat(entity).isNotNull();
		assertThat(entity.getId()).isNull();
		assertThat(entity.getName()).isEqualTo(name);
		verifyZeroInteractions(fooMapper.genericDao);
	}

	@Test
	public void getEntity_should_return_existing_entity_corresponding_to_dto_is_null() {
		// GIVEN
		long id = 1L;
		String name = "foo";

		FooDto dto = new FooDto();
		dto.setId(id);
		dto.setName(name);

		FooModel model = new FooModel();
		model.setId(id);
		when(fooMapper.genericDao.find(FooModel.class, id)).thenReturn(model);

		// WHEN
		FooModel entity = fooMapper.getEntity(dto);

		// THEN
		assertThat(entity).isNotNull();
		assertThat(entity.getId()).isEqualTo(1L);
		assertThat(entity.getName()).isEqualTo(name);
		verify(fooMapper.genericDao).find(any(Class.class), anyLong());
	}

	@Test
	public void getEntities_should_return_empty_list_if_dtos_is_null() {
		// GIVEN
		Collection<FooDto> dtos = null;

		// WHEN
		Collection<FooModel> entities = fooMapper.getEntities(dtos);

		// THEN
		assertThat(entities).isNotNull().isEmpty();
		verifyZeroInteractions(fooMapper.genericDao);
	}

	@Test
	public void getEntities_should_return_empty_list_if_dtos_is_an_empty_list() {
		// GIVEN
		Collection<FooDto> dtos = emptyList();

		// WHEN
		Collection<FooModel> entities = fooMapper.getEntities(dtos);

		// THEN
		assertThat(entities).isNotNull().isEmpty();
		verifyZeroInteractions(fooMapper.genericDao);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void getEntities_should_return_entities_corresponding_to_dtos() {
		// GIVEN
		long id1 = 1L;
		String name1 = "foo";
		FooDto dto1 = new FooDto();
		dto1.setId(id1);
		dto1.setName(name1);

		long id2 = 2L;
		String name2 = "bar";
		FooDto dto2 = new FooDto();
		dto2.setId(id2);
		dto2.setName(name2);

		String name3 = "foobar";
		FooDto dto3 = new FooDto();
		dto3.setName(name3);

		Collection<FooDto> dtos = asList(dto1, dto2, dto3);

		FooModel model1 = new FooModel();
		model1.setId(id1);

		FooModel model2 = new FooModel();
		model2.setId(id2);

		Map<Long, FooModel> index = new HashMap<Long, FooModel>();
		index.put(id1, model1);
		index.put(id2, model2);

		when(fooMapper.genericDao.indexById(index.keySet())).thenReturn((Map) index);

		// WHEN
		Collection<FooModel> entities = fooMapper.getEntities(dtos);

		// THEN
		assertThat(entities).hasSameSizeAs(dtos);

		List<FooModel> lst = new ArrayList<FooModel>(entities);
		assertThat(lst.get(0).getId()).isEqualTo(id1);
		assertThat(lst.get(0).getName()).isEqualTo(name1);
		assertThat(lst.get(1).getId()).isEqualTo(id2);
		assertThat(lst.get(1).getName()).isEqualTo(name2);
		assertThat(lst.get(2).getId()).isNull();
		assertThat(lst.get(2).getName()).isEqualTo(name3);
	}
}
