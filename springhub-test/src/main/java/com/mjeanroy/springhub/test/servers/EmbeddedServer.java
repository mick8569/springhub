package com.mjeanroy.springhub.test.servers;

public interface EmbeddedServer {

	/** Start embedded server. */
	void start();

	/** Stop embedded server. */
	void stop();

	/** Restart embedded server. */
	void restart();

	/**
	 * Check if embedded server is started.
	 *
	 * @return True if embedded server is started, false otherwise.
	 */
	boolean isStarted();

	/**
	 * Get port used by embedded server.
	 *
	 * @return Port.
	 */
	int getPort();
}
