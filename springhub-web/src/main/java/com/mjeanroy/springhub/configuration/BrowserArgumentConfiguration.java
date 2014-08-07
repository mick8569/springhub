package com.mjeanroy.springhub.configuration;

import com.mjeanroy.springhub.configuration.resolvers.BrowserWebArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import java.util.List;

@Slf4j
@Configuration
public class BrowserArgumentConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		log.info("Add browser argument resolver");
		BrowserWebArgumentResolver argResolver = browserWebArgumentResolver();
		ServletWebArgumentResolverAdapter adapter = new ServletWebArgumentResolverAdapter(argResolver);
		argumentResolvers.add(adapter);
	}

	@Bean
	private BrowserWebArgumentResolver browserWebArgumentResolver() {
		return new BrowserWebArgumentResolver();
	}
}
