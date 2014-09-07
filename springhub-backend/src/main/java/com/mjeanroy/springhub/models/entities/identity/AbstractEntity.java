package com.mjeanroy.springhub.models.entities.identity;

import com.mjeanroy.springhub.models.AbstractModel;
import com.mjeanroy.springhub.models.Model;
import com.mjeanroy.springhub.models.entities.JPAEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract implementation of jpa entity using an id primary.
 * Primary key used {@link javax.persistence.GenerationType#IDENTITY} generated value.
 */
@MappedSuperclass
public abstract class AbstractEntity extends AbstractModel implements Model, JPAEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, precision = 22, scale = 0)
	protected Long id;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
