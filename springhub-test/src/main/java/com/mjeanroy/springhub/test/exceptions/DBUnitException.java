package com.mjeanroy.springhub.test.exceptions;

public class DBUnitException extends RuntimeException {

	public DBUnitException(Exception ex) {
		super(ex);
	}
}
