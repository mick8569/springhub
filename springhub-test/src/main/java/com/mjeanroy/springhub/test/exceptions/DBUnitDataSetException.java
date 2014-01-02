package com.mjeanroy.springhub.test.exceptions;

public class DBUnitDataSetException extends RuntimeException {

	public DBUnitDataSetException(Exception ex) {
		super(ex);
	}
}
