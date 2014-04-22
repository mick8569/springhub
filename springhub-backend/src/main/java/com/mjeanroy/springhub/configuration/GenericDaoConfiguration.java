package com.mjeanroy.springhub.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mjeanroy.springhub.dao.GenericDao;

@Configuration
public class GenericDaoConfiguration {

	/** Class logger. */
	private static final Logger log = LoggerFactory.getLogger(GenericDaoConfiguration.class);

	@Bean
	public GenericDao genericDao() {
		log.info("Initialize generic dao bean");
		return new GenericDao();
	}
}
