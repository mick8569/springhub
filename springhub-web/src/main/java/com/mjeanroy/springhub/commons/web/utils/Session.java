package com.mjeanroy.springhub.commons.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Session extends CookieSession {

	/** Application Salt. */
	private final byte[] salt;

	/** Application Secret Key. */
	private final byte[] secret;

	/**
	 * Build new session object.
	 *
	 * @param request  Http Request.
	 * @param response Http Response.
	 * @param salt     Application Salt.
	 * @param secret   Application Secret Key?
	 */
	public Session(HttpServletRequest request, HttpServletResponse response, String salt, String secret) {
		super(request, response, true);
		this.salt = salt.getBytes();
		this.secret = secret.getBytes();
	}

	@Override
	protected String salt() {
		return new String(salt);
	}

	@Override
	protected String secret() {
		return new String(secret);
	}
}
