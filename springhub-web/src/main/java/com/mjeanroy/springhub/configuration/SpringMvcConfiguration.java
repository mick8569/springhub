package com.mjeanroy.springhub.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public abstract class SpringMvcConfiguration extends WebMvcConfigurationSupport {

	/** Class logger. */
	private static final Logger log = LoggerFactory.getLogger(SpringMvcConfiguration.class);

	@Bean
	public MultipartResolver multipartResolver() {
		long maxUploadSize = maxUploadSize();

		log.info("Initialize multipart resolver");
		log.debug("- Max upload size: {}", maxUploadSize);
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(maxUploadSize());
		return multipartResolver;
	}

	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		boolean alwaysUseFullPath = true;
		boolean useSuffixPatternMatch = false;

		log.info("Initialize request mapping handler mapping");
		log.debug("- Always use full path: {}", alwaysUseFullPath);
		log.debug("- Use suffix pattern match: {}", useSuffixPatternMatch);

		RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
		handlerMapping.setAlwaysUseFullPath(alwaysUseFullPath);
		handlerMapping.setUseSuffixPatternMatch(useSuffixPatternMatch);
		return handlerMapping;
	}

	/**
	 * Specifiy max upload size for multipart resolver.
	 * Default value is -1 (no limit).
	 *
	 * @return Maximum upload size.
	 */
	public long maxUploadSize() {
		return -1;
	}
}
