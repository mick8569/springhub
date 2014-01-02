package com.mjeanroy.springhub.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mjeanroy.springhub.commons.web.utils.Browser;
import com.mjeanroy.springhub.exceptions.DisconnectedException;
import com.mjeanroy.springhub.exceptions.EmailUniqueException;
import com.mjeanroy.springhub.exceptions.EntityNotFoundException;
import com.mjeanroy.springhub.exceptions.NotImplementedException;
import com.mjeanroy.springhub.exceptions.RequestParameterException;
import com.mjeanroy.springhub.exceptions.ResourceNotFoundException;
import com.mjeanroy.springhub.exceptions.UnauthorizedException;

@Controller
public abstract class AbstractController {

	/** Class logger */
	private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

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

	@ExceptionHandler(DisconnectedException.class)
	public void disconnectedException(DisconnectedException ex, HttpServletResponse response) throws IOException {
		log.error(ex.getMessage());
		setResponse(response, 401, ex.getMessage());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public void unauthorizedException(UnauthorizedException ex, HttpServletResponse response) throws IOException {
		log.error(ex.getMessage());
		setResponse(response, 403, ex.getMessage());
	}

	@ExceptionHandler(RequestParameterException.class)
	public void requestParameterException(RequestParameterException ex, HttpServletResponse response) {
		log.error(ex.getMessage(), ex);
		setResponse(response, 400, ex.getMessage());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public void entityNotFoundException(EntityNotFoundException ex, HttpServletResponse response) {
		log.error(ex.getMessage());
		setResponse(response, 500, ex.getMessage());
	}

	@ExceptionHandler(NotImplementedException.class)
	public void notImplementedException(NotImplementedException ex, HttpServletResponse response) {
		log.error(ex.getMessage(), ex);
		setResponse(response, 501, ex.getMessage());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public void resourceNotFoundException(ResourceNotFoundException ex, HttpServletResponse response) {
		log.error(ex.getMessage());
		setResponse(response, 404, ex.getMessage());
	}

	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> methodArgumentNotValid(MethodArgumentNotValidException ex) throws IOException {
		log.error(ex.getMessage());
		return bindingResultsToErrors(ex.getBindingResult());
	}

	@ExceptionHandler(value = {BindException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> bindException(BindException ex, HttpServletResponse response) throws IOException {
		log.error(ex.getMessage());
		return bindingResultsToErrors(ex.getBindingResult());
	}

	@ExceptionHandler(value = {InvalidFormatException.class})
	public void invalidFormatException(InvalidFormatException ex, HttpServletResponse response) throws IOException {
		log.error(ex.getMessage());
		setResponse(response, 400, ex.getMessage());
	}

	@ExceptionHandler(value = {EmailUniqueException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> emailUniqueException(EmailUniqueException ex) throws IOException {
		log.error(ex.getMessage());

		Map<String, String> errors = new HashMap<String, String>();
		errors.put("email", ex.getMessage());
		return errors;
	}

	private Map<String, String> bindingResultsToErrors(BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<String, String>();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			String objectName = fieldError.getField();
			String defaultMessage = fieldError.getDefaultMessage();
			errors.put(objectName, defaultMessage);
		}
		return errors;
	}

	protected void setResponse(HttpServletResponse response, int status, String message) {
		response.setStatus(status);

		try {
			response.getWriter().print(message);
		}
		catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		}
	}
}
