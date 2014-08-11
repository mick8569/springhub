package com.mjeanroy.springhub.configuration.resolvers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.mjeanroy.springhub.commons.web.utils.Session;

public class SessionWebArgumentResolver implements WebArgumentResolver {

	/** Salt used to decrypt session value. */
	private final byte[] salt;

	/** Secret key used to decrypt session value. */
	private final byte[] secret;

	public SessionWebArgumentResolver(String salt, String secret) {
		this.salt = salt.getBytes();
		this.secret = secret.getBytes();
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
