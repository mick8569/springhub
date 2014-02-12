package com.mjeanroy.springhub.exceptions;

public class ReflectionException extends RuntimeException {

	public ReflectionException(Exception ex) {
		super(ex);
	}
}
