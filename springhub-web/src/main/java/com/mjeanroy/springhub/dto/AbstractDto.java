package com.mjeanroy.springhub.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mjeanroy.springhub.commons.web.json.Json;
import com.mjeanroy.springhub.models.entities.JPAEntity;
import org.apache.commons.beanutils.BeanMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AbstractDto<MODEL extends JPAEntity> implements Serializable {

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
	 * @param id New {@link #id}
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
		return getId() == null || getId().equals(0L);
	}

	/**
	 * Serialize DTO to a map.
	 *
	 * @return Map containing DTO properties.
	 */
	public Map<String, Object> toMap() {
		Map<String, Object> mapStr = toModifiableMap();
		return unmodifiableMap(mapStr);
	}

	/**
	 * Serialize DTO to a map.
	 *
	 * @param excludes Properties to exclude.
	 * @return Map containing DTO properties.
	 */
	public Map<String, Object> toMapExcluding(Collection<String> excludes) {
		Map<String, Object> mapStr = toModifiableMap();
		for (String exclude : excludes) {
			mapStr.remove(exclude);
		}
		return unmodifiableMap(mapStr);
	}

	private Map<String, Object> toModifiableMap() {
		Map<String, Object> mapStr = new HashMap<String, Object>();

		Map<Object, Object> map = new BeanMap(this);
		for (Map.Entry entry : map.entrySet()) {
			String key = entry.getKey().toString();
			mapStr.put(key, entry.getValue());
		}

		mapStr.remove("class");

		return mapStr;
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
