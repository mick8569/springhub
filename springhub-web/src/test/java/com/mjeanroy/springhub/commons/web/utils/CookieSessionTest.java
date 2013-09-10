package com.mjeanroy.springhub.commons.web.utils;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieSessionTest {

	@Test
	public void test_put() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

		Session session = new Session(request, response);
		session.put("id", "1");

		ArgumentCaptor<Cookie> argument = ArgumentCaptor.forClass(Cookie.class);
		Mockito.verify(response).addCookie(argument.capture());

		Cookie cookie = argument.getValue();
		Assertions.assertThat(cookie).isNotNull();
		Assertions.assertThat(cookie.getPath()).isNotNull().isEqualTo("/");
		Assertions.assertThat(cookie.getName()).isNotNull().isEqualTo("id");
		Assertions.assertThat(cookie.getValue()).isNotNull().isEqualTo("ZvnSZ7HTSao9e%2FmyIKPDcw%3D%3D");
		Assertions.assertThat(cookie.getMaxAge()).isEqualTo(-1);
	}

	@Test
	public void test_put_persistent() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

		Session session = new Session(request, response);
		session.put("id", "1", true);

		ArgumentCaptor<Cookie> argument = ArgumentCaptor.forClass(Cookie.class);
		Mockito.verify(response).addCookie(argument.capture());

		Cookie cookie = argument.getValue();
		Assertions.assertThat(cookie).isNotNull();
		Assertions.assertThat(cookie.getPath()).isNotNull().isEqualTo("/");
		Assertions.assertThat(cookie.getName()).isNotNull().isEqualTo("id");
		Assertions.assertThat(cookie.getValue()).isNotNull().isEqualTo("ZvnSZ7HTSao9e%2FmyIKPDcw%3D%3D");
		Assertions.assertThat(cookie.getMaxAge()).isEqualTo(Integer.MAX_VALUE);
	}

	@Test
	public void test_remove() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

		Session session = new Session(request, response);
		session.remove("id");

		ArgumentCaptor<Cookie> argument = ArgumentCaptor.forClass(Cookie.class);
		Mockito.verify(response).addCookie(argument.capture());

		Cookie cookie = argument.getValue();
		Assertions.assertThat(cookie).isNotNull();
		Assertions.assertThat(cookie.getPath()).isNotNull().isEqualTo("/");
		Assertions.assertThat(cookie.getName()).isNotNull().isEqualTo("id");
		Assertions.assertThat(cookie.getValue()).isNotNull().isEqualTo("");
		Assertions.assertThat(cookie.getMaxAge()).isEqualTo(0);
	}

	@Test
	public void test_get() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

		Cookie cookie1 = new Cookie("foo", "ZvnSZ7HTSao9e%2FmyIKPDcw%3D%3D");
		Cookie cookie2 = new Cookie("bar", "ZvnSZ7HTSao9e%2FmyIKPDcw");
		Cookie[] cookies = {cookie1, cookie2};
		Mockito.when(request.getCookies()).thenReturn(cookies);

		Session session = new Session(request, response);
		String value = session.get("foo");
		Assertions.assertThat(value).isNotNull().isEqualTo("1");
	}

	private static class Session extends CookieSession {
		public Session(HttpServletRequest request, HttpServletResponse response) {
			super(request, response);
		}

		@Override
		protected String salt() {
			return "fnt";
		}

		@Override
		protected String secret() {
			return "ag1yxzwlmryjhp%o";
		}
	}
}
