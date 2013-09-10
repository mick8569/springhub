package com.mjeanroy.springhub.configuration;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public abstract class SpringMvcConfiguration extends WebMvcConfigurationSupport {

	public abstract long maxUploadSize();

	@Bean
	public Mapper mapper() {
		return new DozerBeanMapper();
	}

	@Bean
	public MultipartResolver multipartResolver() {
		MultipartResolver multipartResolver = new CommonsMultipartResolver();
		((CommonsMultipartResolver) multipartResolver).setMaxUploadSize(maxUploadSize());
		return multipartResolver;
	}

	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
		handlerMapping.setAlwaysUseFullPath(true);
		handlerMapping.setUseSuffixPatternMatch(false);
		return handlerMapping;
	}
}
