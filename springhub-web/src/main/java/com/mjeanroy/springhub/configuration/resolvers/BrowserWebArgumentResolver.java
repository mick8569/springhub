package com.mjeanroy.springhub.configuration.resolvers;

import com.mjeanroy.springhub.commons.web.utils.Browser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

public class BrowserWebArgumentResolver implements WebArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		Class klass = methodParameter.getParameterType();

		if (Browser.class.isAssignableFrom(klass)) {
			HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
			return new Browser(request);
		}

		return UNRESOLVED;
	}
}
