package com.mjeanroy.springhub.utils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mjeanroy.springhub.models.entities.identity.AbstractEntity;

@Entity
@Table(name = "foo")
public class FooEntity extends AbstractEntity<Long> {

	@Column(name = "name", nullable = false)
	private String name;

	public FooEntity() {
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
