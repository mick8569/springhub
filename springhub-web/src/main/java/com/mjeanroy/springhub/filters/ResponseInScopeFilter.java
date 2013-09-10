package com.mjeanroy.springhub.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseInScopeFilter implements Filter {

	// not the most elegant, but our spring commiter friends suggested this way.
	private ThreadLocal<HttpServletResponse> responses = new ThreadLocal<HttpServletResponse>();

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		responses.set(response);
		chain.doFilter(servletRequest, servletResponse);
		responses.remove();
	}

	/** Only to be used by the BeanFactory */
	public HttpServletResponse getHttpServletResponse() {
		return responses.get();
	}

	public void destroy() {
	}
}