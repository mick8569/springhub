package com.mjeanroy.springhub.models;

import java.io.Serializable;

/**
 * Interface describing a model object.
 * Model object can be a JPA entity or any objects
 * that can be identified by an id.
 *
 * @param <PK> Generic type of id.
 */
public interface Model<PK extends Serializable> {

	/**
	 * Get id of model object.
	 *
	 * @return Id.
	 */
	PK getId();

	/**
	 * Check if model object is new.
	 *
	 * @return True if model object is new, false otherwise.
	 */
	boolean isNew();
}
