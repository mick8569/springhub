package com.mjeanroy.springhub.test.rules;

import org.junit.rules.ExternalResource;

import com.mjeanroy.springhub.test.servers.EmbeddedJetty;
import com.mjeanroy.springhub.test.servers.EmbeddedServer;

public class ServerRule extends ExternalResource {

	/** Embedded server. */
	private EmbeddedServer server;

	/** Create new rule with jetty as default embedded server. */
	public ServerRule() {
		this(new EmbeddedJetty());
	}

	/**
	 * Create new rule with embedded server.
	 *
	 * @param server Embedded server to use.
	 */
	public ServerRule(EmbeddedServer server) {
		this.server = server;
	}

	protected void before() {
		start();
	}

	protected void after() {
		stop();
	}

	/**
	 * Get port used by embedded server.
	 *
	 * @return Port used by embedded server.
	 */
	public int getPort() {
		return server.getPort();
	}

	/**
	 * Check if embedded server is started.
	 *
	 * @return True if embedded server is started, false otherwise.
	 */
	public boolean isStarted() {
		return server.isStarted();
	}

	/** Start embedded server. */
	public void start() {
		server.start();
	}

	/** Stop embedded server. */
	public void stop() {
		server.stop();
	}

	/** Restart embedded server. */
	public void restart() {
		server.restart();
	}
}
