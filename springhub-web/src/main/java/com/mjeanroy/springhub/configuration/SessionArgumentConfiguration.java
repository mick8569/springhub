package com.mjeanroy.springhub.configuration;

import com.mjeanroy.springhub.configuration.resolvers.SessionWebArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import java.util.List;

@Slf4j
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
@Configuration
public class SessionArgumentConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment environment;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		log.info("Add session argument resolver");
		SessionWebArgumentResolver argResolver = sessionArgumentResolver();
		ServletWebArgumentResolverAdapter adapter = new ServletWebArgumentResolverAdapter(argResolver);
		argumentResolvers.add(adapter);
	}

	@Bean
	private SessionWebArgumentResolver sessionArgumentResolver() {
		String salt = salt();
		String secret = secret();
		return new SessionWebArgumentResolver(salt, secret);
	}

	protected String salt() {
		return environment.getProperty("application.salt");
	}

	protected String secret() {
		return environment.getProperty("application.secret");
	}
}
