package com.mick8569.springhub.models.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public abstract class AbstractEntity extends AbstractGenericEntity<Long> {

	/** Id of entity */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, precision = 22, scale = 0)
	protected Long id;

	public AbstractEntity() {
		super();
	}

	@Override
	public Long entityId() {
		return id;
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
}
