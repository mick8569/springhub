package com.mjeanroy.springhub.mappers;

import com.mjeanroy.springhub.dto.AbstractDto;

public class FooDto extends AbstractDto<Long> {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
