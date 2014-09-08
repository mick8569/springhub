package com.mjeanroy.springhub.utils;

import com.mjeanroy.springhub.models.AbstractModel;

public class FooModel extends AbstractModel<Long> {

	private Long id;

	public FooModel() {
		id = null;
	}

	public FooModel(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
