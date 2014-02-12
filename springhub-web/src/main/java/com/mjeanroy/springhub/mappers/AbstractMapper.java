package com.mjeanroy.springhub.mappers;

import com.mjeanroy.springhub.commons.reflections.ReflectionUtils;
import com.mjeanroy.springhub.dao.GenericDao;
import com.mjeanroy.springhub.dto.AbstractDto;
import com.mjeanroy.springhub.models.AbstractModel;
import com.mjeanroy.springhub.models.entities.AbstractEntity;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mjeanroy.springhub.commons.collections.CollectionsUtils.size;

@Component
public class AbstractMapper<MODEL extends AbstractModel, DTO extends AbstractDto> {

	private static final Logger log = LoggerFactory.getLogger(AbstractMapper.class);

	/** Model's class */
	private Class<MODEL> modelClass;

	/** Dto's class */
	private Class<DTO> dtoClass;

	@Inject
	protected GenericDao genericDao;

	@Inject
	protected Mapper mapper;

	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
	protected MODEL createEntity(DTO dto) {
		MODEL model = null;

		if (this.modelClass.isAssignableFrom(AbstractEntity.class)) {
			model = (MODEL) genericDao.find(this.modelClass.asSubclass(AbstractEntity.class), dto.getId());
		} else {
			try {
				model = this.modelClass.newInstance();
			} catch (InstantiationException ex) {
				log.error(ex.getMessage(), ex);
				return null;
			} catch (IllegalAccessException ex) {
				log.error(ex.getMessage(), ex);
				return null;
			}
		}

		mapper.map(dto, model);
		return model;
	}

	/**
	 * Convert a list of entities to a list of dtos.
	 *
	 * @param entities Entities to convert.
	 * @return Converted dtos.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<DTO> getDtos(Collection<MODEL> entities) {
		int size = size(entities);
		List<DTO> dtos = new ArrayList<DTO>(size);
		if (size > 0) {
			for (MODEL MODEL : entities) {
				DTO dto = getDto(MODEL);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	/**
	 * Convert a list of dtos to a list of entities.
	 *
	 * @param dtos DTOs to convert.
	 * @return Converted entities.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<MODEL> getEntities(Collection<DTO> dtos) {
		int size = size(dtos);
		List<MODEL> entities = new ArrayList<MODEL>(size);
		if (size > 0) {
			for (DTO dto : dtos) {
				MODEL MODEL = getEntity(dto);
				entities.add(MODEL);
			}
		}
		return entities;
	}
}
