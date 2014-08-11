package com.mjeanroy.springhub.configuration.resolvers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.Serializable;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.mjeanroy.springhub.commons.web.utils.Session;
import com.mjeanroy.springhub.exceptions.DisconnectedException;
import com.mjeanroy.springhub.security.AuthenticatedUser;
import com.mjeanroy.springhub.security.AuthenticationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticatedWebArgumentResolver<PK extends Serializable> implements WebArgumentResolver {

	/** Salt used to decrypt session value. */
	private final AuthenticationService<PK> auth;

	/** Salt used to parse session value. */
	private final byte[] salt;

	/** Secret key used to parse session value. */
	private final byte[] secret;

	/** Cookie name used to store session value. */
	private final String cookieName;

	public AuthenticatedWebArgumentResolver(AuthenticationService<PK> auth, byte[] salt, byte[] secret, String cookieName) {
		this.auth = auth;
		this.salt = salt;
		this.cookieName = cookieName;
		this.secret = secret;
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		Class klass = methodParameter.getParameterType();

		if (AuthenticatedUser.class.isAssignableFrom(klass)) {
			Authenticated annotation = methodParameter.getMethodAnnotation(Authenticated.class);
			boolean required = annotation == null || annotation.required();
			log.debug("Retrieve authenticated user (required: {})", required);

			HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
			HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();
			Session session = new Session(request, response, salt, secret);
			String value = session.get(cookieName);

			PK id = value == null ? null : auth.parseAuthenticationId(value.toCharArray());
			AuthenticatedUser authenticatedUser = id != null ? auth.findByAuthenticationId(id) : null;

			if (authenticatedUser == null && required) {
				log.debug("Authenticated user is required but it cannot be found in session");
				throw new DisconnectedException("Authenticated user is required but no one is connected");
			}

			return authenticatedUser;
		}

		return UNRESOLVED;
	}
}
