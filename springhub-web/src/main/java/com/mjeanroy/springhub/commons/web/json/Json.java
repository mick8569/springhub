package com.mjeanroy.springhub.commons.web.json;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Json {

	/** Class logger */
	private static final Logger LOG = LoggerFactory.getLogger(Json.class);

	/**
	 * Get object from JSON stream.
	 *
	 * @param str    JSON stream.
	 * @param result Class type of returned object.
	 * @return The object.
	 */
	public static <T> T fromJson(String str, Class<T> result) {
		try {
			JsonFactory jsonFactory = new JsonFactory();
			JsonParser jp = jsonFactory.createJsonParser(str);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(jp, result);
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
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
			LOG.error(ex.getMessage(), ex);
			return null;
		}
	}
}
