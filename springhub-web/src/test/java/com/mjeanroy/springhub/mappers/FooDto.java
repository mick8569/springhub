package com.mjeanroy.springhub.mappers;

import com.mjeanroy.springhub.dto.AbstractDto;

public class FooDto extends AbstractDto<FooModel> {

	private Long id;

	private String name;

	public Long getId() {
		return id;
	}

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
