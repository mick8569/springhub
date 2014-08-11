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
	@Deprecated
	public Session(HttpServletRequest request, HttpServletResponse response, String salt, String secret) {
		this(request, response, salt.getBytes(), secret.getBytes());
	}

	/**
	 * Build new session object.
	 *
	 * @param request  Http Request.
	 * @param response Http Response.
	 * @param salt     Application Salt.
	 * @param secret   Application Secret Key?
	 */
	public Session(HttpServletRequest request, HttpServletResponse response, byte[] salt, byte[] secret) {
		super(request, response, true);
		this.salt = salt;
		this.secret = secret;
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
