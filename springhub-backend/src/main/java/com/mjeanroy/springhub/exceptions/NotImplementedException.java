package com.mjeanroy.springhub.exceptions;

public class NotImplementedException extends ApplicationException {

	public NotImplementedException(String message) {
		super(message);
	}

	@Override
	public String getType() {
		return "NOT_IMPLEMENTED";
	}
}
