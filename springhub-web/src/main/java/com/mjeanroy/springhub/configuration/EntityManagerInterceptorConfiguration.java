package com.mjeanroy.springhub.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

@Slf4j
@Configuration
public class EntityManagerInterceptorConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("Add entity manager in view");
		OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = openEntityManagerInViewInterceptor();
		WebRequestHandlerInterceptorAdapter adapter = new WebRequestHandlerInterceptorAdapter(openEntityManagerInViewInterceptor);
		registry.addInterceptor(adapter);
	}

	@Bean
	public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
		return new OpenEntityManagerInViewInterceptor();
	}
}
