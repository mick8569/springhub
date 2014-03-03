package com.mjeanroy.springhub.test.db;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class HSQL extends AbstractEmbeddedDB implements DB {

	public HSQL() {
		builder = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL);
	}
}
