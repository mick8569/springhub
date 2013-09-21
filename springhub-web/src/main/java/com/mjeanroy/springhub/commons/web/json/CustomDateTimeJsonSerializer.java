package com.mjeanroy.springhub.commons.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mjeanroy.springhub.commons.dates.DateSerialization;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomDateTimeJsonSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {
		String formattedDate = DateSerialization.serializeDateTime(date);
		gen.writeString(formattedDate);
	}
}
