package com.mjeanroy.springhub.test.db;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class HSQLTest {

	@Test
	public void should_initialize_hsql_builder() {
		// WHEN
		HSQL hsql = new HSQL();

		// THEN
		assertThat(hsql.builder).isNotNull();
		assertThat(hsql.db).isNull();
	}

	@Test
	public void isStarted_should_return_false_if_db_is_null() {
		// GIVEN
		HSQL hsql = new HSQL();
		hsql.db = null;

		// WHEN
		boolean isStarted = hsql.isStarted();

		// THEN
		assertThat(isStarted).isFalse();
	}

	@Test
	public void isStarted_should_return_true_if_db_is_not_null() {
		// GIVEN
		HSQL hsql = new HSQL();
		hsql.db = mock(EmbeddedDatabase.class);

		// WHEN
		boolean isStarted = hsql.isStarted();

		// THEN
		assertThat(isStarted).isTrue();
	}

	@Test
	public void start_should_start_embedded_db() {
		// GIVEN
		HSQL hsql = new HSQL();

		// WHEN
		DataSource ds = hsql.start();

		// THEN
		assertThat(ds).isNotNull().isSameAs(hsql.db);
		hsql.db.shutdown();
	}

	@Test
	public void shutdown_should_stop_embedded_db() {
		// GIVEN
		HSQL hsql = new HSQL();
		EmbeddedDatabase db = mock(EmbeddedDatabase.class);
		hsql.db = db;

		// WHEN
		hsql.shutdown();

		// THEN
		verify(db).shutdown();
		assertThat(hsql.db).isNull();
	}

	@Test
	public void addInitScript_should_add_init_script() {
		// GIVEN
		HSQL hsql = new HSQL();
		hsql.builder = mock(EmbeddedDatabaseBuilder.class);
		String script = "/foo";

		// WHEN
		hsql.addInitScript(script);

		// THEN
		verify(hsql.builder).addScript(script);
	}

	@Test(expected = RuntimeException.class)
	public void addInitScript_should_fail_if_database_is_already_started() {
		// GIVEN
		HSQL hsql = new HSQL();
		hsql.builder = mock(EmbeddedDatabaseBuilder.class);
		hsql.db = mock(EmbeddedDatabase.class);
		String script = "/foo";

		// WHEN
		hsql.addInitScript(script);
	}
}
