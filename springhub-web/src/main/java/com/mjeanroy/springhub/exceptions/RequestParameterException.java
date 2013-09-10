package com.mjeanroy.springhub.exceptions;

public class RequestParameterException extends ApplicationException {

	private RequestParameterExceptionType type;

	public RequestParameterException() {
		super("Request parameters are not valid");
		this.type = RequestParameterExceptionType.INVALID_PARAMETER;
	}

	public RequestParameterException(RequestParameterExceptionType type, String msg) {
		super(msg);
		this.type = type;
	}

	@Override
	public String getType() {
		return "REQUEST_" + this.type.toString();
	}

	public static enum RequestParameterExceptionType {
		INVALID_PARAMETER,
		EMPTY_PARAMETER
	}
}
