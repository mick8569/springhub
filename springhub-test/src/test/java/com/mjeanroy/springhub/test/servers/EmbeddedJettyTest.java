package com.mjeanroy.springhub.test.servers;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Server.class)
public class EmbeddedJettyTest {

	@Test
	public void it_should_initialize_server() {
		// WHEN
		EmbeddedJetty jetty = new EmbeddedJetty();

		// THEN
		assertThat(jetty.server).isNotNull();
		assertThat(jetty.server.getStopAtShutdown()).isTrue();
		assertThat(jetty.server.isStarted()).isFalse();
		assertThat(jetty.webapp).isEqualTo("src/main/webapp");
	}

	@Test
	public void it_should_start_server() throws Exception {
		// GIVEN
		EmbeddedJetty jetty = new EmbeddedJetty();
		jetty.server = mock(Server.class);

		// WHEN
		jetty.start();

		// THEN
		assertThat(jetty.isStarted()).isTrue();
		verify(jetty.server).start();

		ArgumentCaptor<WebAppContext> argCtx = ArgumentCaptor.forClass(WebAppContext.class);
		verify(jetty.server).setHandler(argCtx.capture());

		WebAppContext ctx = argCtx.getValue();
		assertThat(ctx).isNotNull();
		assertThat(ctx.getServer()).isEqualTo(jetty.server);
		assertThat(ctx.getContextPath()).isEqualTo("/");
		assertThat(ctx.getWar()).isEqualTo("src/main/webapp");
	}

	@Test
	@Ignore
	public void it_should_stop_server() throws Exception {
		// GIVEN
		EmbeddedJetty jetty = new EmbeddedJetty();
		jetty.server = mock(Server.class);
		jetty.started = true;

		// WHEN
		jetty.stop();

		// THEN
		assertThat(jetty.isStarted()).isFalse();
		verify(jetty.server).stop();
		verify(jetty.server).join();
	}

	@Test
	public void it_should_return_port() throws Exception {
		// GIVEN
		int localPort = 9999;
		ServerConnector serverConnector = mock(ServerConnector.class);
		when(serverConnector.getLocalPort()).thenReturn(localPort);

		EmbeddedJetty jetty = new EmbeddedJetty();
		jetty.server = mock(Server.class);
		when(jetty.server.getConnectors()).thenReturn(new Connector[]{serverConnector});

		// WHEN
		int port = jetty.getPort();

		// THEN
		assertThat(port).isEqualTo(localPort);
	}

	@Test
	public void it_should_restart_server() throws Exception {
		// GIVEN
		EmbeddedJetty jetty = new EmbeddedJetty();
		jetty.server = mock(Server.class);
		jetty.started = true;

		// WHEN
		jetty.restart();

		// THEN
		InOrder inOrder = inOrder(jetty.server);
		inOrder.verify(jetty.server).stop();
		inOrder.verify(jetty.server).start();
	}
}
