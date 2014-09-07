package com.mjeanroy.springhub.models;

/**
 * Interface describing a model object.
 * Model object can be a JPA entity or any objects
 * that can be identified by an id.
 *
 * TODO use generic type of id
 */
public interface Model {

	/**
	 * Get id of model object.
	 *
	 * @return Id.
	 */
	Long getId();

	/**
	 * Check if model object is new.
	 *
	 * @return True if model object is new, false otherwise.
	 */
	boolean isNew();
}
