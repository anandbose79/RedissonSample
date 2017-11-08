package com.anand.redisson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RCacheEvict {
	//Evict everything or specific keys
	 
    String cacheName() default "default";
    String key() default "";
    String scope() default "KEY";
}
