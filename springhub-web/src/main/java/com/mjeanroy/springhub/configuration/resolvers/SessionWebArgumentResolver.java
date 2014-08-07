package com.mjeanroy.springhub.configuration.resolvers;

import com.mjeanroy.springhub.commons.web.utils.Session;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionWebArgumentResolver implements WebArgumentResolver {

	private final String salt;

	private final String secret;

	public SessionWebArgumentResolver(String salt, String secret) {
		this.salt = salt;
		this.secret = secret;
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		Class klass = methodParameter.getParameterType();

		if (klass.equals(Session.class)) {
			HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
			HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();
			return new Session(request, response, salt, secret);
		}

		return UNRESOLVED;
	}
}
