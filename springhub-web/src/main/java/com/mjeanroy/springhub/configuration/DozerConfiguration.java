package com.mjeanroy.springhub.configuration;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfiguration {

	/** Class logger. */
	private static final Logger log = LoggerFactory.getLogger(DozerConfiguration.class);

	@Bean(destroyMethod = "destroy")
	public Mapper mapper() {
		log.info("Initialize dozer bean mapper");
		return new DozerBeanMapper();
	}
}
