package com.mjeanroy.springhub.security;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mjeanroy.springhub.commons.web.utils.Session;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityInterceptor<PK extends Serializable> implements HandlerInterceptor {

	/** Secret key used by cookie */
	private final byte[] secret;

	/** Salt used by cookie */
	private final byte[] salt;

	/** Cookie name used for session security check */
	private final String cookieName;

	/** Authentication Service used to parse session value. */
	private final AuthenticationService<PK> auth;

	public SecurityInterceptor(AuthenticationService<PK> auth, byte[] salt, byte[] secret, String cookieName) {
		this.auth = auth;
		this.secret = secret;
		this.salt = salt;
		this.cookieName = cookieName;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod method = (HandlerMethod) handler;

		Security methodAnnotation = method.getMethodAnnotation(Security.class);
		if (methodAnnotation != null) {
			log.debug("Secured request, check for session");

			Session session = new Session(request, response, salt, secret);
			String value = session.get(cookieName);
			PK id = value != null ? auth.parseAuthenticationId(value.toCharArray()) : null;

			if (id == null) {
				log.debug("No session id available or session id is not valid");
				if (isNotEmpty(methodAnnotation.redirectTo())) {
					log.debug("Redirect to page '{}'", methodAnnotation.redirectTo());
					response.sendRedirect(methodAnnotation.redirectTo());
				} else {
					log.debug("Return unauthorized status");
					response.setStatus(UNAUTHORIZED.value());
				}
				return false;
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
	}
}
