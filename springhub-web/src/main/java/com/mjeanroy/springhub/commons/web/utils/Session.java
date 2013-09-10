package com.mjeanroy.springhub.commons.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Session extends CookieSession {

	private String salt;

	private String secret;

	public Session(HttpServletRequest request, HttpServletResponse response, String salt, String secret) {
		super(request, response, true);
		this.salt = salt;
		this.secret = secret;
	}

	@Override
	protected String salt() {
		return salt;
	}

	@Override
	protected String secret() {
		return secret;
	}
}
