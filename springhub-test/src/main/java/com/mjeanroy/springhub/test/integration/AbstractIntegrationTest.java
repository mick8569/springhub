package com.mjeanroy.springhub.test.integration;

import javax.sql.DataSource;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mjeanroy.springhub.commons.context.SpringApplicationContext;
import com.mjeanroy.springhub.test.db.AbstractDatabaseTest;

@Ignore
public abstract class AbstractIntegrationTest extends AbstractDatabaseTest {

	/** Port of test server */
	public static final int PORT = 8888;

	private static final Logger log = LoggerFactory.getLogger(AbstractIntegrationTest.class);

	/** Jetty Server */
	public static Server server;

	@BeforeClass
	public static void beforeClass() throws Exception {
		startServer();
	}

	/**
	 * Start jetty server
	 *
	 * @throws Exception If an error occurred during server initialization.
	 */
	protected static void startServer() throws Exception {
		log.info("Start jetty embedded server");

		server = new Server(PORT);

		WebAppContext ctx = new WebAppContext();
		ctx.setContextPath("/");
		ctx.setWar("src/main/webapp");
		ctx.setServer(server);
		server.setHandler(ctx);
		server.setStopAtShutdown(true);

		server.start();

		log.info("Jetty successfully started");
	}

	@AfterClass
	public static void afterClass() throws Exception {
		stopServer();
	}

	/**
	 * Stop jetty server.
	 *
	 * @throws Exception If an error occurred during server stop.
	 */
	protected static void stopServer() throws Exception {
		log.info("Stop jetty");

		server.stop();
		server.join();

		if (server.getHandlers() != null) {
			for (org.eclipse.jetty.server.Handler handler : server.getHandlers()) {
				handler.stop();
				handler.destroy();
			}
		}

		server.destroy();
		server = null;

		log.info("Jetty successfully stopped");
	}

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
}
