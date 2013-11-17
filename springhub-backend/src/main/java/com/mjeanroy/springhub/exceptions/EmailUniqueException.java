package com.mjeanroy.springhub.exceptions;

@SuppressWarnings("serial")
public class EmailUniqueException extends RuntimeException {

	private String email;

	public EmailUniqueException(String email) {
		super("Email '" + email + "' is already used");
		this.email = email;
	}

	public EmailUniqueException(String email, String message, Throwable cause) {
		super(message, cause);
		this.email = email;
	}

	public EmailUniqueException(String email, String message) {
		super(message);
		this.email = email;
	}

	public EmailUniqueException(String email, Throwable cause) {
		super(cause);
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
