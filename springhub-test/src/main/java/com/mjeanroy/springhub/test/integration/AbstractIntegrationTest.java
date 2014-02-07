package com.mjeanroy.springhub.test.integration;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mjeanroy.springhub.commons.context.SpringApplicationContext;
import com.mjeanroy.springhub.test.db.AbstractDatabaseTest;
import com.mjeanroy.springhub.test.rules.ServerRule;

@Ignore
public abstract class AbstractIntegrationTest extends AbstractDatabaseTest {

	/** Jetty Server */
	@ClassRule
	public static ServerRule server = new ServerRule();

	@Before
	public void setUp() throws Exception {
		this.datasource = (DataSource) SpringApplicationContext.getBean("dataSource");
		this.jdbcTemplate = new JdbcTemplate(this.datasource);
		super.startHsqlDb();
	}

	@After
	public void tearDown() throws Exception {
		stopHsqlDb();
	}

	public int getPort() {
		return server.getPort();
	}
}
