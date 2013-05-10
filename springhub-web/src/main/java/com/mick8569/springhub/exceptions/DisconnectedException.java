package com.mick8569.springhub.exceptions;

public class DisconnectedException extends ApplicationException {

	public DisconnectedException(String message) {
		super(message);
	}

	@Override
	public String getType() {
		return "DISCONNECTED";
	}
}
