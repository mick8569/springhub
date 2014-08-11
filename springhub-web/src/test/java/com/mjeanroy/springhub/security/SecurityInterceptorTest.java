package com.mjeanroy.springhub.security;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.method.HandlerMethod;

@SuppressWarnings("unchecked")
public class SecurityInterceptorTest {

	private AuthenticationService authenticationService;

	private SecurityInterceptor securityInterceptor;

	@Before
	public void setUp() {
		authenticationService = mock(AuthenticationService.class);
		securityInterceptor = new SecurityInterceptor(authenticationService, "fnt".getBytes(), "ag1yxzwlmryjhp%o".getBytes(), "SESSIONID");
	}

	@Test
	public void testSecurityInterceptor_should_throw_401_without_cookie() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HandlerMethod handler = mock(HandlerMethod.class);

		Security securityAnnotation = mock(Security.class);
		when(handler.getMethodAnnotation(Security.class)).thenReturn(securityAnnotation);

		boolean result = securityInterceptor.preHandle(request, response, handler);

		assertThat(result).isFalse();
		verify(response).setStatus(401);
	}

	@Test
	public void testSecurityInterceptor_should_redirect_without_cookie() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HandlerMethod handler = mock(HandlerMethod.class);

		Security securityAnnotation = mock(Security.class);
		when(securityAnnotation.redirectTo()).thenReturn("/login");
		when(handler.getMethodAnnotation(Security.class)).thenReturn(securityAnnotation);

		boolean result = securityInterceptor.preHandle(request, response, handler);

		assertThat(result).isFalse();
		verify(response).sendRedirect("/login");
	}

	@Test
	public void testSecurityInterceptor_should_authenticate_with_valid_cookie() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HandlerMethod handler = mock(HandlerMethod.class);

		Security securityAnnotation = Mockito.mock(Security.class);
		when(securityAnnotation.redirectTo()).thenReturn("/login");
		when(handler.getMethodAnnotation(Security.class)).thenReturn(securityAnnotation);

		Cookie cookie1 = new Cookie("SESSIONID", "ZvnSZ7HTSao9e%2FmyIKPDcw%3D%3D");
		Cookie cookie2 = new Cookie("bar", "ZvnSZ7HTSao9e%2FmyIKPDcw");
		Cookie[] cookies = {cookie1, cookie2};
		when(request.getCookies()).thenReturn(cookies);

		when(authenticationService.parseAuthenticationId("1".toCharArray())).thenReturn(1L);

		boolean result = securityInterceptor.preHandle(request, response, handler);

		assertThat(result).isTrue();
		verify(authenticationService).parseAuthenticationId("1".toCharArray());
		verify(response, never()).sendRedirect(anyString());
		verify(response, never()).setStatus(anyInt());
	}

	@Test
	public void testSecurityInterceptor_without_security_should_continue() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HandlerMethod handler = mock(HandlerMethod.class);

		when(handler.getMethodAnnotation(Security.class)).thenReturn(null);

		boolean result = securityInterceptor.preHandle(request, response, handler);

		assertThat(result).isTrue();
		verify(response, never()).sendRedirect(anyString());
		verify(response, never()).setStatus(anyInt());
	}
}
