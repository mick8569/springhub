package com.mjeanroy.springhub.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		GenericDaoConfiguration.class,
		BeanValidationConfiguration.class,
		ApplicationContextHookConfiguration.class
})
public class BackendConfiguration {

}
