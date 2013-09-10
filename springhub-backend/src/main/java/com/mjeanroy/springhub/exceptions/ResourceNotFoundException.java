package com.mjeanroy.springhub.exceptions;

public class ResourceNotFoundException extends ApplicationException {

	public ResourceNotFoundException() {
		super("Requested resource is not found");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	@Override
	public String getType() {
		return "RESOURCE_NOT_FOUND";
	}
}
