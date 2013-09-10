package com.mjeanroy.springhub.exceptions;

public abstract class ApplicationException extends RuntimeException {

	/** Exception message */
	protected String message;

	/**
	 * Build new exception.
	 *
	 * @param ex      Original exception.
	 * @param message Message.
	 */
	public ApplicationException(Exception ex, String message) {
		super(message, ex);
		this.message = message;
	}

	/**
	 * Build new exception.
	 *
	 * @param ex Original exception.
	 */
	public ApplicationException(Exception ex) {
		super(ex);
		this.message = ex.getMessage();
	}

	/**
	 * Build new exception.
	 *
	 * @param message Message.
	 */
	public ApplicationException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * Get identifier of error.
	 *
	 * @return Identifier of error.
	 */
	public abstract String getType();

	/**
	 * Get {@link #message}
	 *
	 * @return {@link #message}
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Get error object associated to this exception.
	 *
	 * @return Error object.
	 */
	public ApplicationErrorMessage getError() {
		return new ApplicationErrorMessage(this.getType(), this.message);
	}
}
