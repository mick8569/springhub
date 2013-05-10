package com.mick8569.springhub.commons.web.utils;

import com.mick8569.springhub.commons.crypto.Crypto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CookieSession {

	/** Http Request */
	private HttpServletRequest request;

	/** Http Response */
	private HttpServletResponse response;

	/**
	 * Build http session from http request and response.
	 *
	 * @param request  Http request.
	 * @param response Http response.
	 */
	public CookieSession(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	/**
	 * Decrypt cookie value.
	 *
	 * @param encrypted Encrypted value.
	 * @return Decrypted value.
	 */
	private String decryptCookieValue(String encrypted) {
		return Crypto.decryptAES_UTF8(encrypted, salt(), secret());
	}

	/**
	 * Encrypt cookie value.
	 *
	 * @param value Decrypted value.
	 * @return Encrypted value.
	 */
	private String encryptCookieValue(String value) {
		return Crypto.encryptAES_UTF8(value, salt(), secret());
	}

	/**
	 * Salt value use to encrypt cookie.
	 *
	 * @return Salt value.
	 */
	protected abstract String salt();

	/**
	 * Secret use to encrypt cookie.
	 *
	 * @return Secret value.
	 */
	protected abstract String secret();

	/**
	 * Put new item in session.
	 *
	 * @param name  Name of item.
	 * @param value Value of item.
	 */
	public Cookie put(String name, String value) {
		return put(name, value, false);
	}

	/**
	 * Put new item in session.
	 *
	 * @param name      Name of item.
	 * @param value     Value of item.
	 * @param permanent True to use a permanent session, aka session will not be clear when browser will close.
	 */
	public Cookie put(String name, String value, boolean permanent) {
		String encryptedVal = encryptCookieValue(value);
		Cookie cookie = new Cookie(name, encryptedVal);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		if (permanent) {
			cookie.setMaxAge(Integer.MAX_VALUE);
		}
		response.addCookie(cookie);
		return cookie;
	}

	/**
	 * Get item from session and immediately remove the item from session.
	 *
	 * @param name Name of item to get.
	 * @return Found value, null if cannot be found.
	 */
	public String getAndRemove(String name) {
		String val = get(name);
		remove(name);
		return val;
	}

	/**
	 * Get item from session.
	 *
	 * @param name Name of item to get.
	 * @return Found value, null if cannot be found.
	 */
	public String get(String name) {
		Cookie[] cookies = request.getCookies();
		if ((cookies == null) || (cookies.length == 0)) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				String value = cookie.getValue();
				return decryptCookieValue(value);
			}
		}
		return null;
	}

	/**
	 * Remove item from session.
	 *
	 * @param name Name of item to remove.
	 */
	public void remove(String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
