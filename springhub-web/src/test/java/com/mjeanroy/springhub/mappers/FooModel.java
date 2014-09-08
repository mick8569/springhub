package com.mjeanroy.springhub.mappers;

import com.mjeanroy.springhub.models.entities.identity.AbstractEntity;

public class FooModel extends AbstractEntity<Long> {

	private String name;

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
