package com.mjeanroy.springhub.configuration.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.mjeanroy.springhub.configuration.AuthenticatedUserArgumentConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AuthenticatedUserArgumentConfiguration.class)
public @interface EnableAuthenticatedArgument {

}
