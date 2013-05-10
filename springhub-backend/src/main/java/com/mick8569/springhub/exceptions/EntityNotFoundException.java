package com.mick8569.springhub.exceptions;

public class EntityNotFoundException extends ApplicationException {

	public EntityNotFoundException(String message) {
		super(message);
	}

	@Override
	public String getType() {
		return "NOT_FOUND";
	}
}
