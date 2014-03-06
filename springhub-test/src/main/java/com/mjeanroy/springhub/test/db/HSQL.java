package com.mjeanroy.springhub.test.db;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class HSQL extends AbstractEmbeddedDB implements DB {

	protected static HSQL hsql;

	public static HSQL instance() {
		if (hsql == null) {
			hsql = new HSQL();
		}
		return hsql;
	}

	private HSQL() {
		builder = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL);
	}
}
