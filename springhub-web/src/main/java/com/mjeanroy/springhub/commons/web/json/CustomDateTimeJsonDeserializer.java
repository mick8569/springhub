package com.mjeanroy.springhub.commons.web.json;

import com.mjeanroy.springhub.commons.dates.DateSerialization;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomDateTimeJsonDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String text = jp.getText();
		return DateSerialization.deserializeDateTime(text);
	}

}
