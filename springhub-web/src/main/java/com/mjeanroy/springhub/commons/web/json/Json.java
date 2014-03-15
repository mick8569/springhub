package com.mjeanroy.springhub.commons.web.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Json {

	/** Class logger */
	private static final Logger log = LoggerFactory.getLogger(Json.class);

	/** Json Object Mapper (it is thread safe). */
	private static final ObjectMapper mapper = new ObjectMapper();

	private Json() {
	}

	/**
	 * Get object from JSON stream.
	 *
	 * @param str    JSON stream.
	 * @param result Class type of returned object.
	 * @return The object.
	 */
	public static <T> T fromJson(String str, Class<T> result) {
		try {
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
			return mapper.writeValueAsString(from);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}
}
