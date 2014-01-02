package com.mjeanroy.springhub.commons.web.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Json {

	/** Class logger */
	private static final Logger log = LoggerFactory.getLogger(Json.class);

	/**
	 * Get object from JSON stream.
	 *
	 * @param str    JSON stream.
	 * @param result Class type of returned object.
	 * @return The object.
	 */
	public static <T> T fromJson(String str, Class<T> result) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(str, result);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * Serialize an object to a json stream.
	 *
	 * @param from Object to serialize.
	 * @return The json stream.
	 */
	public static String toJson(Object from) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(from);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}
}
