package com.mjeanroy.springhub.test.rules;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.InOrder;

import com.mjeanroy.springhub.test.servers.EmbeddedServer;

public class ServerRuleTest {

	@Test
	public void test_execute_test() throws Throwable {
		// GIVEN
		EmbeddedServer server = mock(EmbeddedServer.class);
		ServerRule serverRule = new ServerRule(server);

		Statement statement = mock(Statement.class);
		Description description = mock(Description.class);

		// WHEN
		Statement result = serverRule.apply(statement, description);

		// THEN
		verify(server, never()).start();
		verify(server, never()).stop();
		verifyZeroInteractions(statement);
		verifyZeroInteractions(description);

		result.evaluate();

		InOrder inOrder = inOrder(server, statement);
		inOrder.verify(server).start();
		inOrder.verify(statement).evaluate();
		inOrder.verify(server).stop();
	}

	@Test
	public void it_should_get_server_port() {
		// GIVEN
		int serverPort = 9999;
		EmbeddedServer server = mock(EmbeddedServer.class);
		when(server.getPort()).thenReturn(serverPort);
		ServerRule serverRule = new ServerRule(server);

		// WHEN
		int port = serverRule.getPort();

		// THEN
		assertThat(port).isEqualTo(serverPort);
		verify(server).getPort();
	}

	@Test
	public void it_should_stop_server() {
		// GIVEN
		EmbeddedServer server = mock(EmbeddedServer.class);
		ServerRule serverRule = new ServerRule(server);

		// WHEN
		serverRule.stop();

		// THEN
		verify(server).stop();
	}

	@Test
	public void it_should_start_server() {
		// GIVEN
		EmbeddedServer server = mock(EmbeddedServer.class);
		ServerRule serverRule = new ServerRule(server);

		// WHEN
		serverRule.start();

		// THEN
		verify(server).start();
	}

	@Test
	public void it_should_restart_server() {
		// GIVEN
		EmbeddedServer server = mock(EmbeddedServer.class);
		ServerRule serverRule = new ServerRule(server);

		// WHEN
		serverRule.restart();

		// THEN
		verify(server).restart();
	}

	@Test
	public void it_should_check_if_server_is_started() {
		// GIVEN
		boolean serverIsStarted = true;
		EmbeddedServer server = mock(EmbeddedServer.class);
		when(server.isStarted()).thenReturn(serverIsStarted);
		ServerRule serverRule = new ServerRule(server);

		// WHEN
		boolean isStarted = serverRule.isStarted();

		// THEN
		assertThat(isStarted).isEqualTo(serverIsStarted);
		verify(server).isStarted();
	}
}
