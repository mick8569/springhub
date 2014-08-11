package com.mjeanroy.springhub.configuration.resolvers;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.*;
import static org.springframework.web.bind.support.WebArgumentResolver.UNRESOLVED;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

import com.mjeanroy.springhub.exceptions.DisconnectedException;
import com.mjeanroy.springhub.security.AuthenticatedUser;
import com.mjeanroy.springhub.security.AuthenticationService;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class AuthenticatedWebArgumentResolverTest {

	@Rule
	public ExpectedException thrown = none();

	@Mock
	private AuthenticationService authenticationService;

	@Mock
	private NativeWebRequest webRequest;

	@Mock
	private MethodParameter parameter;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private Authenticated authenticated;

	private AuthenticatedWebArgumentResolver resolver;

	@Before
	public void setUp() {
		when(webRequest.getNativeRequest()).thenReturn(request);
		when(webRequest.getNativeResponse()).thenReturn(response);

		String cookieName = "SESSIONID";
		Cookie cookie1 = new Cookie(cookieName, "ZvnSZ7HTSao9e%2FmyIKPDcw%3D%3D");
		Cookie[] cookies = {cookie1};
		when(request.getCookies()).thenReturn(cookies);

		resolver = new AuthenticatedWebArgumentResolver(authenticationService, "fnt".getBytes(), "ag1yxzwlmryjhp%o".getBytes(), cookieName);
	}

	@Test
	public void it_should_be_unresolved_with_other_type() throws Exception {
		Class klass = Foo.class;
		when(parameter.getParameterType()).thenReturn(klass);

		Object result = resolver.resolveArgument(parameter, webRequest);

		assertThat(result).isNotNull().isSameAs(UNRESOLVED);
		verifyZeroInteractions(webRequest);
		verifyZeroInteractions(authenticationService);
	}

	@Test
	public void it_should_return_user() throws Exception {
		Class klass = User.class;
		when(parameter.getParameterType()).thenReturn(klass);

		User user = new User();

		Long authId = user.getAuthenticationId();
		char[] value = authId.toString().toCharArray();
		when(authenticationService.parseAuthenticationId(value)).thenReturn(authId);
		when(authenticationService.findByAuthenticationId(authId)).thenReturn(user);

		Object result = resolver.resolveArgument(parameter, webRequest);

		assertThat(result).isNotNull().isEqualTo(user);
		verify(authenticationService).parseAuthenticationId(value);
		verify(authenticationService).findByAuthenticationId(authId);
	}

	@Test
	public void it_should_return_null_if_user_is_not_required_and_session_value_is_null() throws Exception {
		when(request.getCookies()).thenReturn(new Cookie[] {});
		when(authenticated.required()).thenReturn(false);

		Class klass = User.class;
		when(parameter.getParameterType()).thenReturn(klass);
		when(parameter.getMethodAnnotation(Authenticated.class)).thenReturn(authenticated);

		Object result = resolver.resolveArgument(parameter, webRequest);

		assertThat(result).isNull();
		verify(authenticationService, never()).parseAuthenticationId((char[]) any());
		verify(authenticationService, never()).findByAuthenticationId(anyLong());
	}

	@Test
	public void it_should_return_null_if_user_is_not_required_and_session_value_is_not_valid() throws Exception {
		when(authenticated.required()).thenReturn(false);

		Class klass = User.class;
		when(parameter.getParameterType()).thenReturn(klass);
		when(parameter.getMethodAnnotation(Authenticated.class)).thenReturn(authenticated);

		char[] value = "1".toCharArray();
		when(authenticationService.parseAuthenticationId(value)).thenReturn(null);

		Object result = resolver.resolveArgument(parameter, webRequest);

		assertThat(result).isNull();
		verify(authenticationService).parseAuthenticationId(value);
		verify(authenticationService, never()).findByAuthenticationId(anyLong());
	}

	@Test
	public void it_should_return_null_if_user_is_not_found() throws Exception {
		when(authenticated.required()).thenReturn(false);

		Class klass = User.class;
		when(parameter.getParameterType()).thenReturn(klass);
		when(parameter.getMethodAnnotation(Authenticated.class)).thenReturn(authenticated);

		char[] value = "1".toCharArray();
		when(authenticationService.parseAuthenticationId(value)).thenReturn(1L);
		when(authenticationService.findByAuthenticationId(1L)).thenReturn(null);

		Object result = resolver.resolveArgument(parameter, webRequest);

		assertThat(result).isNull();
		verify(authenticationService).parseAuthenticationId(value);
		verify(authenticationService).findByAuthenticationId(1L);
	}

	@Test
	public void it_should_fail_if_session_is_null_and_user_is_required() throws Exception {
		thrown.expect(DisconnectedException.class);

		when(request.getCookies()).thenReturn(new Cookie[]{});
		when(authenticated.required()).thenReturn(true);

		Class klass = User.class;
		when(parameter.getParameterType()).thenReturn(klass);
		when(parameter.getMethodAnnotation(Authenticated.class)).thenReturn(authenticated);

		resolver.resolveArgument(parameter, webRequest);
	}

	@Test
	public void it_should_fail_if_session_value_is_not_valid_and_user_is_required() throws Exception {
		thrown.expect(DisconnectedException.class);
		when(authenticated.required()).thenReturn(true);

		Class klass = User.class;
		when(parameter.getParameterType()).thenReturn(klass);
		when(parameter.getMethodAnnotation(Authenticated.class)).thenReturn(authenticated);

		char[] value = "1".toCharArray();
		when(authenticationService.parseAuthenticationId(value)).thenReturn(null);

		resolver.resolveArgument(parameter, webRequest);
	}

	@Test
	public void it_should_fail_if_user_is_not_found_and_is_required() throws Exception {
		thrown.expect(DisconnectedException.class);
		when(authenticated.required()).thenReturn(true);

		Class klass = User.class;
		when(parameter.getParameterType()).thenReturn(klass);
		when(parameter.getMethodAnnotation(Authenticated.class)).thenReturn(authenticated);

		char[] value = "1".toCharArray();
		when(authenticationService.parseAuthenticationId(value)).thenReturn(1L);
		when(authenticationService.findByAuthenticationId(1L)).thenReturn(null);

		resolver.resolveArgument(parameter, webRequest);
	}

	private static class Foo {

	}

	private static class User implements AuthenticatedUser<Long> {

		@Override
		public Long getAuthenticationId() {
			return 1L;
		}
	}
}
