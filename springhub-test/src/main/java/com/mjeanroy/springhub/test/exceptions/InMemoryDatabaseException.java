package com.mjeanroy.springhub.test.exceptions;

public class InMemoryDatabaseException extends RuntimeException {

	public InMemoryDatabaseException(Exception ex) {
		super(ex);
	}
}
