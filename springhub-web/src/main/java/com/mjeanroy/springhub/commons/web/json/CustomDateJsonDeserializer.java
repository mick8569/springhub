package com.mjeanroy.springhub.commons.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.mjeanroy.springhub.commons.dates.DateSerialization;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomDateJsonDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		String text = jp.getText();
		return DateSerialization.deserializeDateTime(text, "yyyy-MM-dd");
	}

}
