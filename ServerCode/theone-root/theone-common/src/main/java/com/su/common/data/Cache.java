package com.su.common.data;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface Cache {
	boolean memoryCache() default false;
	boolean redisCache() default false;
	long startId();
}
