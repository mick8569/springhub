package com.mjeanroy.springhub.exceptions;

public class StorageException extends ApplicationException {

	public StorageException(String message) {
		super(message);
	}

	public StorageException(Exception ex) {
		super(ex);
	}

	@Override
	public String getType() {
		return "STORAGE";
	}
}
