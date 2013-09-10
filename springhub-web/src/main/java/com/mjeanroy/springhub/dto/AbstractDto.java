package com.mjeanroy.springhub.dto;

import com.mjeanroy.springhub.commons.web.json.Json;
import com.mjeanroy.springhub.models.AbstractModel;
import org.apache.commons.beanutils.BeanMap;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
