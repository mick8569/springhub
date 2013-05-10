package com.mick8569.springhub.exceptions;

import org.apache.commons.lang3.StringUtils;

public class ApplicationErrorMessage {

	/** Identifier of message */
	private String type;

	/** Error message */
	private String message;

	/**
	 * Build new error message.
	 *
	 * @param type    Type of message (identifier).
	 * @param message Message.
	 */
	public ApplicationErrorMessage(String type, String message) {
		this.type = type;
		this.message = message;
	}

	/**
	 * Get {@link #type}
	 *
	 * @return {@link #type}
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Get {@link #message}
	 *
	 * @return {@link #message}
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Get error message serialize as JSON object.
	 *
	 * @return JSON representation of error.
	 */
	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"type\":");
		sb.append(getType());

		String message = getMessage();
		if (StringUtils.isNotBlank(message)) {
			sb.append(",");
			sb.append("\"message\":");
			sb.append(message);
		}

		sb.append("}");
		return sb.toString();
	}
}
