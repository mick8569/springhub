package com.mick8569.springhub.controllers;

import com.mick8569.springhub.commons.web.utils.Browser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public abstract class AbstractController {

	@Autowired
	protected HttpServletRequest request;

	/**
	 * Get browser information for current request.
	 *
	 * @return Browser details.
	 */
	protected Browser browser() {
		return new Browser(request);
	}

	/**
	 * Check if client browser is Internet Explorer less or equals to version 9.
	 *
	 * @return True if client browser is Internet Explorer less or equals to version 9.
	 */
	protected boolean ltIE9() {
		return new Browser(request).ltIE9();
	}

	/**
	 * Check if client browser is Internet Explorer less or equals to version 8.
	 *
	 * @return True if client browser is Internet Explorer less or equals to version 8.
	 */
	protected boolean ltIE8() {
		return new Browser(request).ltIE8();
	}

	/**
	 * Check if client browser is Internet Explorer less or equals to version 7.
	 *
	 * @return True if client browser is Internet Explorer less or equals to version 7.
	 */
	protected boolean ltIE7() {
		return new Browser(request).ltIE7();
	}

	/**
	 * Check if client browser is Internet Explorer less or equals to version 6.
	 *
	 * @return True if client browser is Internet Explorer less or equals to version 6.
	 */
	protected boolean ltIE6() {
		return new Browser(request).ltIE6();
	}
}
