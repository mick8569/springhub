package com.mjeanroy.springhub.test.servers;

public class EmbeddedServerException extends RuntimeException {

	public EmbeddedServerException(Exception ex) {
		super(ex);
	}
}
