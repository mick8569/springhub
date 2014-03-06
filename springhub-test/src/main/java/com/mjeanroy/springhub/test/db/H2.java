package com.mjeanroy.springhub.test.db;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class H2 extends AbstractEmbeddedDB implements DB {

	protected static H2 h2;

	public static H2 instance() {
		if (h2 == null) {
			h2 = new H2();
		}
		return h2;
	}

	private H2() {
		builder = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2);
	}
}
