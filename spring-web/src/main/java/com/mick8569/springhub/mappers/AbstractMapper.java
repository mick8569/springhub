package com.mick8569.springhub.mappers;

import com.mick8569.springhub.commons.reflections.ReflectionUtils;
import com.mick8569.springhub.dao.GenericDao;
import com.mick8569.springhub.dto.AbstractDto;
import com.mick8569.springhub.models.entities.AbstractEntity;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Component
public class AbstractMapper<MODEL extends AbstractEntity, DTO extends AbstractDto<MODEL>> {

	/** Model's class */
	private Class<MODEL> modelClass;

	/** Dto's class */
	private Class<DTO> dtoClass;

	@Inject
	private GenericDao genericDao;

	@Inject
	private Mapper mapper;

	public AbstractMapper() {
		super();
		this.modelClass = (Class<MODEL>) ReflectionUtils.getGenericType(getClass(), 0);
		this.dtoClass = (Class<DTO>) ReflectionUtils.getGenericType(getClass(), 1);
	}

	/**
	 * Get {@link #modelClass}
	 *
	 * @return {@link #modelClass}
	 */
	protected Class<MODEL> getModelClass() {
		return modelClass;
	}

	/**
	 * Get {@link #dtoClass}
	 *
	 * @return {@link #dtoClass}
	 */
	protected Class<DTO> getDtoClass() {
		return dtoClass;
	}

	/**
	 * Convert an MODEL to a DTO.
	 *
	 * @param model Entity to convert.
	 * @return Converted DTO.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public DTO getDto(MODEL model) {
		if (model == null) {
			return null;
		}
		return createDto(model);
	}

	/**
	 * Convert a DTO to an entity.
	 *
	 * @param dto DTO to convert.
	 * @return Converted entity.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public MODEL getEntity(DTO dto) {
		if (dto == null) {
			return null;
		}
		return createEntity(dto);
	}

	/**
	 * Convert an MODEL to a DTO.<br>
	 * Entity given in parameter cannot be null.
	 *
	 * @param model Entity to convert.
	 * @return Converted DTO.
	 */
	protected DTO createDto(MODEL model) {
		return mapper.map(model, dtoClass);
	}

	/**
	 * Convert a DTO to an entity.<br>
	 * DTO given in parameter cannot be null.
	 *
	 * @param dto DTO to convert.
	 * @return Converted entity.
	 */
	protected MODEL createEntity(DTO dto) {
		Long id = dto.getId();
		MODEL model = (MODEL) genericDao.find(this.modelClass.asSubclass(AbstractEntity.class), id);
		mapper.map(dto, model);
		return model;
	}
}
