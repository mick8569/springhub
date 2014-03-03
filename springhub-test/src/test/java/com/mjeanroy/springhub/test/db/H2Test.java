package com.mjeanroy.springhub.test.db;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class H2Test {

	@Test
	public void should_initialize_h2_builder() {
		// WHEN
		H2 h2 = new H2();

		// THEN
		assertThat(h2.builder).isNotNull();
		assertThat(h2.db).isNull();
	}

	@Test
	public void isStarted_should_return_false_if_db_is_null() {
		// GIVEN
		H2 h2 = new H2();
		h2.db = null;

		// WHEN
		boolean isStarted = h2.isStarted();

		// THEN
		assertThat(isStarted).isFalse();
	}

	@Test
	public void isStarted_should_return_true_if_db_is_not_null() {
		// GIVEN
		H2 h2 = new H2();
		h2.db = mock(EmbeddedDatabase.class);

		// WHEN
		boolean isStarted = h2.isStarted();

		// THEN
		assertThat(isStarted).isTrue();
	}

	@Test
	public void start_should_start_embedded_db() {
		// GIVEN
		H2 h2 = new H2();

		// WHEN
		DataSource ds = h2.start();

		// THEN
		assertThat(ds).isNotNull().isSameAs(h2.db);
		h2.db.shutdown();
	}

	@Test
	public void shutdown_should_stop_embedded_db() {
		// GIVEN
		H2 h2 = new H2();
		EmbeddedDatabase db = mock(EmbeddedDatabase.class);
		h2.db = db;

		// WHEN
		h2.shutdown();

		// THEN
		verify(db).shutdown();
		assertThat(h2.db).isNull();
	}

	@Test
	public void addInitScript_should_add_init_script() {
		// GIVEN
		H2 h2 = new H2();
		h2.builder = mock(EmbeddedDatabaseBuilder.class);
		String script = "/foo";

		// WHEN
		h2.addInitScript(script);

		// THEN
		verify(h2.builder).addScript(script);
	}

	@Test(expected = RuntimeException.class)
	public void addInitScript_should_fail_if_database_is_already_started() {
		// GIVEN
		H2 h2 = new H2();
		h2.builder = mock(EmbeddedDatabaseBuilder.class);
		h2.db = mock(EmbeddedDatabase.class);
		String script = "/foo";

		// WHEN
		h2.addInitScript(script);
	}
}
