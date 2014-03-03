package com.mjeanroy.springhub.test.db;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class H2 implements DB {

	/** Database builder. */
	protected EmbeddedDatabaseBuilder builder;

	/** Target database. */
	protected EmbeddedDatabase db;

	public H2() {
		builder = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2);
	}

	@Override
	public boolean isStarted() {
		return db != null;
	}

	@Override
	public DB addInitScript(String script) {
		if (isStarted()) {
			throw new RuntimeException("Initialization script must be added before database initialization");
		}
		builder.addScript(script);
		return this;
	}

	@Override
	public DataSource start() {
		if (!isStarted()) {
			db = builder.build();
		}
		return db;
	}

	@Override
	public void shutdown() {
		if (isStarted()) {
			db.shutdown();
			db = null;
		}
	}

	@Override
	public DataSource dataSource() {
		return db;
	}
}
