package com.mjeanroy.springhub.exceptions;

public class UnauthorizedException extends ApplicationException {

	public UnauthorizedException(String message) {
		super(message);
	}

	@Override
	public String getType() {
		return "UNAUTHORIZED";
	}
}
