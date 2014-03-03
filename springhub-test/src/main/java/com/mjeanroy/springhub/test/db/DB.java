package com.mjeanroy.springhub.test.db;

import javax.sql.DataSource;

public interface DB {

	/**
	 * Check if database has been started.
	 *
	 * @return True if database has been started, false otherwise.
	 */
	boolean isStarted();

	/** Initialize database. */
	DataSource start();

	/** Shutdown connection to database. */
	void shutdown();

	/**
	 * Get target data source.
	 *
	 * @return Data source.
	 */
	DataSource dataSource();

	/**
	 * Add initialization script.
	 *
	 * @param script Script to add.
	 * @return this.
	 */
	DB addInitScript(String script);
}
