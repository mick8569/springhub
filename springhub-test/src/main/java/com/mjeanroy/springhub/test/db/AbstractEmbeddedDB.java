package com.mjeanroy.springhub.test.db;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public abstract class AbstractEmbeddedDB implements DB {

	/** Database builder. */
	protected EmbeddedDatabaseBuilder builder;

	/** Target database. */
	protected EmbeddedDatabase db;

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
