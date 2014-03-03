package com.mjeanroy.springhub.test.db;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class H2 extends AbstractEmbeddedDB implements DB {

	public H2() {
		builder = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2);
	}
}
