package com.mick8569.springhub.filters;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("rawtypes")
public class HttpServletResponseFactoryBean implements FactoryBean {

	@Autowired
	private ResponseInScopeFilter responseInScopeFilter;

	public Object getObject() throws Exception {
		return responseInScopeFilter.getHttpServletResponse();
	}

	public Class getObjectType() {
		return HttpServletResponse.class;
	}

	public boolean isSingleton() {
		return false;
	}
}