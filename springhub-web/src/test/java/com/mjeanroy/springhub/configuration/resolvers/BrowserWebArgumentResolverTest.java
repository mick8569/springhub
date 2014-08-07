package com.mjeanroy.springhub.configuration.resolvers;

import com.mjeanroy.springhub.commons.web.utils.Browser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.web.bind.support.WebArgumentResolver.UNRESOLVED;

@SuppressWarnings("unchecked")
public class BrowserWebArgumentResolverTest {

	private BrowserWebArgumentResolver resolver;

	@Before
	public void setUp() {
		resolver = new BrowserWebArgumentResolver();
	}

	@Test
	public void it_should_browser_as_argument() throws Exception {
		// GIVEN
		Class klass = Browser.class;
		MethodParameter methodParameter = mock(MethodParameter.class);
		when(methodParameter.getParameterType()).thenReturn(klass);

		HttpServletRequest httpRequest = mock(HttpServletRequest.class);
		NativeWebRequest nativeWebRequest = mock(NativeWebRequest.class);
		when(nativeWebRequest.getNativeRequest()).thenReturn(httpRequest);

		// WHEN
		Object result = resolver.resolveArgument(methodParameter, nativeWebRequest);

		// THEN
		assertThat(result)
				.isNotNull()
				.isInstanceOf(Browser.class);
	}

	@Test
	public void it_should_be_unresolved_if_argument_is_not_browser() throws Exception {
		// GIVEN
		Class klass = Integer.class;
		MethodParameter methodParameter = mock(MethodParameter.class);
		when(methodParameter.getParameterType()).thenReturn(klass);

		HttpServletRequest httpRequest = mock(HttpServletRequest.class);
		NativeWebRequest nativeWebRequest = mock(NativeWebRequest.class);
		when(nativeWebRequest.getNativeRequest()).thenReturn(httpRequest);

		// WHEN
		Object result = resolver.resolveArgument(methodParameter, nativeWebRequest);

		// THEN
		assertThat(result).isSameAs(UNRESOLVED);
	}
}
