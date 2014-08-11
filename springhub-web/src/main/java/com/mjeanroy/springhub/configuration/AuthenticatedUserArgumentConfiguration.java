package com.mjeanroy.springhub.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import com.mjeanroy.springhub.configuration.resolvers.AuthenticatedWebArgumentResolver;
import com.mjeanroy.springhub.security.AuthenticationService;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unchecked")
@Slf4j
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
@Configuration
public class AuthenticatedUserArgumentConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment environment;

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		log.info("Add authenticated user argument resolver");
		AuthenticatedWebArgumentResolver argResolver = authenticatedWebArgumentResolver();
		ServletWebArgumentResolverAdapter adapter = new ServletWebArgumentResolverAdapter(argResolver);
		argumentResolvers.add(adapter);
	}

	@Bean
	private AuthenticatedWebArgumentResolver authenticatedWebArgumentResolver() {
		String salt = salt();
		String secret = secret();
		String cookieName = cookieName();
		return new AuthenticatedWebArgumentResolver(authenticationService, salt.getBytes(), secret.getBytes(), cookieName);
	}

	protected String salt() {
		return environment.getProperty("application.salt");
	}

	protected String secret() {
		return environment.getProperty("application.secret");
	}

	protected String cookieName() {
		return environment.getProperty("application.cookieName");
	}
}
