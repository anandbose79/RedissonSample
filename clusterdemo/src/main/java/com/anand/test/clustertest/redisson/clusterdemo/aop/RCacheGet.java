package com.anand.test.clustertest.redisson.clusterdemo.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RCacheGet {
      String cacheName() default "default";
      String key() default "";
      
      
}