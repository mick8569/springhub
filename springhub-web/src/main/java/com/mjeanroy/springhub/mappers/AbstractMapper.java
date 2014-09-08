package com.mjeanroy.springhub.mappers;

import static com.mjeanroy.springhub.commons.collections.CollectionsUtils.size;
import static java.util.Collections.emptyMap;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mjeanroy.springhub.commons.reflections.ReflectionUtils;
import com.mjeanroy.springhub.dao.GenericDao;
import com.mjeanroy.springhub.dto.AbstractDto;
import com.mjeanroy.springhub.models.entities.JPAEntity;

@Component
public class AbstractMapper<PK extends Serializable, MODEL extends JPAEntity<PK>, DTO extends AbstractDto<PK>> {

	private static final Logger log = LoggerFactory.getLogger(AbstractMapper.class);

	@Inject
	protected GenericDao genericDao;

	@Inject
	protected Mapper mapper;

	/** Model's class */
	private Class<MODEL> modelClass;

	/** Dto's class */
	private Class<DTO> dtoClass;

	@SuppressWarnings("unchecked")
	public AbstractMapper() {
		super();
		this.modelClass = (Class<MODEL>) ReflectionUtils.getGenericType(getClass(), 1);
		this.dtoClass = (Class<DTO>) ReflectionUtils.getGenericType(getClass(), 2);
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
		MODEL model;

		if (!dto.isNew() && JPAEntity.class.isAssignableFrom(modelClass)) {
			model = genericDao.find(modelClass, dto.getId());
		} else {
			try {
				model = this.modelClass.newInstance();
			}
			catch (InstantiationException ex) {
				log.error(ex.getMessage(), ex);
				return null;
			}
			catch (IllegalAccessException ex) {
				log.error(ex.getMessage(), ex);
				return null;
			}
		}

		return fromDto(model, dto);
	}

	protected MODEL fromDto(MODEL model, DTO dto) {
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
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<MODEL> getEntities(Collection<DTO> dtos) {
		int size = size(dtos);
		List<MODEL> entities = new ArrayList<MODEL>(size);
		if (size > 0) {
			Map<PK, DTO> mapDto = indexDtoById(dtos);
			Map<PK, MODEL> mapModel = findEntitiesByIndex(mapDto);
			for (DTO dto : dtos) {
				MODEL entity = buildEntity(mapModel, dto);
				entities.add(entity);
			}
		}
		return entities;
	}

	protected MODEL buildEntity(Map<PK, MODEL> mapModel, DTO dto) {
		MODEL entity = null;
		if (!dto.isNew()) {
			PK id = dto.getId();
			entity = mapModel.get(id);
		}
		return entity == null ? getEntity(dto) : fromDto(entity, dto);
	}

	@SuppressWarnings("unchecked")
	protected Map<PK, MODEL> findEntitiesByIndex(Map<PK, DTO> mapDto) {
		Map<PK, MODEL> mapModel;

		if (mapDto.isEmpty()) {
			mapModel = emptyMap();
		} else {
			mapModel = genericDao.indexById(modelClass, mapDto.keySet());
		}

		return mapModel;
	}

	protected Map<PK, DTO> indexDtoById(Collection<DTO> dtos) {
		Map<PK, DTO> mapDto = new HashMap<PK, DTO>();
		for (DTO dto : dtos) {
			if (!dto.isNew()) {
				PK id = dto.getId();
				mapDto.put(id, dto);
			}
		}
		return mapDto;
	}
}
