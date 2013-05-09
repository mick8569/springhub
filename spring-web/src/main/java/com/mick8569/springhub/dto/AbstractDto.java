package com.mick8569.springhub.dto;

import com.mick8569.springhub.models.entities.AbstractEntity;
import org.apache.commons.beanutils.BeanMap;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class AbstractDto<MODEL extends AbstractEntity> implements Serializable {

	/** DTO identifier */
	protected Long id;

	/** Construct new DTO. */
	public AbstractDto() {
		super();
	}

	/**
	 * Construct new DTO from id of associated Entity.
	 *
	 * @param entity Associated model.
	 */
	public AbstractDto(MODEL entity) {
		super();
		this.id = entity.getId();
	}

	/**
	 * Get {@link #id}
	 *
	 * @return {@link #id}
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set {@link #id}
	 *
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Serialize DTO to a map.
	 *
	 * @return Map containing DTO properties.
	 */
	public Map<String, Object> toMap() {
		return new BeanMap(this);
	}
}
