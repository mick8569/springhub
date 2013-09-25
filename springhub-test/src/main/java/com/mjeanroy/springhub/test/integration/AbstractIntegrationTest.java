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

	/**
	 * Port of test server
	 */
	public static final int PORT = 8888;

	private static final Logger LOG = LoggerFactory.getLogger(AbstractIntegrationTest.class);

	/**
	 * Jetty Server
	 */
	public static Server server;

	@BeforeClass
	public static void beforeClass() throws Exception {
		startServer();
	}

	/**
	 * Start jetty server
	 *
	 * @throws Exception If an error occured during server initialization.
	 */
	protected static void startServer() throws Exception {
		LOG.info("Start jetty embedded server");

		server = new Server(PORT);

		WebAppContext ctx = new WebAppContext();
		ctx.setContextPath("/");
		ctx.setWar("src/main/webapp");
		ctx.setServer(server);
		server.setHandler(ctx);
		server.setStopAtShutdown(true);

		server.start();

		LOG.info("Jetty successfully started");
	}

	@AfterClass
	public static void afterClass() throws Exception {
		stopServer();
	}

	/**
	 * Stop jetty server.
	 *
	 * @throws Exception If an error occured during server stop.
	 */
	protected static void stopServer() throws Exception {
		LOG.info("Stop jetty");

		server.stop();
		server.join();

		if ((server != null) && (server.getHandlers() != null)) {
			for (org.eclipse.jetty.server.Handler handler : server.getHandlers()) {
				handler.stop();
				handler.destroy();
			}
		}

		server.destroy();
		server = null;

		LOG.info("Jetty successfully stopped");
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

	/*
	public HttpResponse sendPost(String path) throws Exception {
		return sendPost(path, new HashMap<String, String>(), true);
	}

	public HttpResponse sendPost(String path, Map<String, String> parameters) throws Exception {
		return sendPost(path, parameters, true);
	}

	public HttpResponse sendPost(String path, Map<String, String> parameters, boolean authenticate) throws Exception {
		return send("POST", path, parameters, authenticate);
	}

	public HttpResponse sendDelete(String path) throws Exception {
		return sendDelete(path, new HashMap<String, String>(), true);
	}

	public HttpResponse sendDelete(String path, Map<String, String> parameters) throws Exception {
		return sendDelete(path, parameters, true);
	}

	public HttpResponse sendDelete(String path, Map<String, String> parameters, boolean authenticate) throws Exception {
		return send("DELETE", path, parameters, authenticate);
	}

	public HttpResponse sendGet(String path) throws Exception {
		return sendGet(path, false);
	}

	public HttpResponse sendGet(String path, boolean authenticate) throws Exception {
		return sendGet(path, new HashMap<String, String>(), authenticate);
	}

	public HttpResponse sendGet(String path, Map<String, String> parameters) throws Exception {
		return sendGet(path, parameters, false);
	}

	public HttpResponse sendGet(String path, Map<String, String> params, boolean authenticate) throws Exception {
		return send("GET", path, params, authenticate);
	}

	public HttpResponse send(String type, String path, Map<String, String> params, boolean authenticate) throws Exception {
		HttpClient client = new HttpClient();

		if (authenticate) {
			Cookie cookie = new Cookie(
					HOST,
					AbstractController.COOKIE_SESSION_NAME,
					Session.encryptCookieValue("1"),
					"/",
					-1,
					false
			);
			client.getState().addCookie(cookie);
		}

		HttpMethodBase method = null;
		if (type.equals("GET")) {
			method = new GetMethod(getUrl(path));
		}
		else if (type.equals("POST")) {
			method = new PostMethod(getUrl(path));
			method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		}
		else if (type.equals("DELETE")) {
			method = new DeleteMethod(getUrl(path));
		}

		if ((params != null) && (!params.isEmpty())) {
			List<NameValuePair> getParameters = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				getParameters.add(new NameValuePair(entry.getKey(), entry.getValue()));
			}

			String queryString = EncodingUtil.formUrlEncode(
					getParameters.toArray(new NameValuePair[getParameters.size()]),
					"UTF-8"
			);

			if (method instanceof GetMethod) {
				method.setQueryString(queryString);
			} else {
				((PostMethod) method).setRequestBody(queryString);
			}
		}

		// Add request for XMLHttpRequest
		method.addRequestHeader("X-Requested-With", "XMLHttpRequest");

		int code = client.executeMethod(method);

		StringBuilder response = new StringBuilder();
		InputStreamReader in = new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8");

		char[] buf = new char[1024];
		int len;
		while ((len = in.read(buf)) > -1) {
			response.append(new String(buf, 0, len));
		}
		in.close();

		return new HttpResponse(code, response.toString());
	}

	public HtmlPage getHtmlPage(String path, boolean authenticate) throws Exception {
		WebClient webClient = new WebClient();
		com.gargoylesoftware.htmlunit.util.Cookie cookie = new com.gargoylesoftware.htmlunit.util.Cookie(
				HOST,
				AbstractController.COOKIE_SESSION_NAME,
				Session.encryptCookieValue("1")
		);
		webClient.getCookieManager().addCookie(cookie);
		return webClient.getPage(getUrl(path));
	}

	public HtmlPage getHtmlPage(String path) throws Exception {
		return getHtmlPage(path, true);
	}

	public String getUrl(String path) {
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		return String.format("http://%s:%d/%s",
				HOST,
				PORT, path);
	}

	public String getContent(String file) throws Exception {
		URL url = getClass().getResource("/integrations/" + file);
		URI uri = url.toURI();
		File f = (new File(uri));
		return FileUtils.readFileToString(f).trim();
	}

	public static class HttpResponse {

		public int status;

		public String response;

		public HttpResponse(int status, String response) {
			this.status = status;
			this.response = response;
		}
	}
	*/
}
