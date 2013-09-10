package com.mjeanroy.springhub.test.mappers;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractMapperTest {

	@Spy
	protected Mapper mapper = new DozerBeanMapper();
}
