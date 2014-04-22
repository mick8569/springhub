package com.mjeanroy.springhub.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		DozerConfiguration.class,
		EntityManagerInterceptorConfiguration.class
})
public class SpringWebConfiguration {

}
