package com.mjeanroy.springhub.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mjeanroy.springhub.commons.context.SpringApplicationContext;

@Configuration
public class ApplicationContextHookConfiguration {

	/** Class logger. */
	private static final Logger log = LoggerFactory.getLogger(ApplicationContextHookConfiguration.class);

	@Bean
	public ApplicationContextAware applicationContext() {
		log.info("Initialize spring application context aware bean");
		return new SpringApplicationContext();
	}
}
