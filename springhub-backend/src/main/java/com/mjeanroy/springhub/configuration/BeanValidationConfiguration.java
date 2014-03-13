package com.mjeanroy.springhub.configuration;

import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class BeanValidationConfiguration {

	/** Class logger. */
	private static final Logger log = LoggerFactory.getLogger(BeanValidationConfiguration.class);

	@Bean
	public ValidatorFactory validator() {
		log.info("Initialize validator factory bean");
		return new LocalValidatorFactoryBean();
	}
}
