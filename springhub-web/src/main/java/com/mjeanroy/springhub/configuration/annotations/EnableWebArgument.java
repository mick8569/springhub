package com.mjeanroy.springhub.configuration.annotations;

import com.mjeanroy.springhub.configuration.BrowserArgumentConfiguration;
import com.mjeanroy.springhub.configuration.SessionArgumentConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
		BrowserArgumentConfiguration.class,
		SessionArgumentConfiguration.class
})
public @interface EnableWebArgument {
}
