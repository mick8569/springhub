package com.mjeanroy.springhub.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mjeanroy.springhub.commons.web.json.Json;
import com.mjeanroy.springhub.models.AbstractModel;
import org.apache.commons.beanutils.BeanMap;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AbstractDto<MODEL extends AbstractModel> implements Serializable {

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
		this.id = entity.modelId();
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
	 * Check if dto is a new object or obtained from an existing model.
	 *
	 * @return True if dto is new, false otherwise.
	 */
	@JsonIgnore
	public boolean isNew() {
		return id == null || id.equals(0L);
	}

	/**
	 * Serialize DTO to a map.
	 *
	 * @return Map containing DTO properties.
	 */
	public Map<String, Object> toMap() {
		return new BeanMap(this);
	}

	/**
	 * Serialize DTO to json string.
	 *
	 * @return JSON value of DTO.
	 */
	public String toJson() {
		return Json.toJson(this);
	}
}
