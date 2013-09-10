package com.mjeanroy.springhub.models.vo;

import com.mjeanroy.springhub.models.AbstractModel;

public abstract class AbstractVO extends AbstractModel {

	public AbstractVO() {
		super();
	}

	public Long getId() {
		return null;
	}

	@Override
	public Long modelId() {
		return getId();
	}
}
