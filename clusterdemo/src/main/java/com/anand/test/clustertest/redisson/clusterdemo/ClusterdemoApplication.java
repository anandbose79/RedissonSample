package com.anand.test.clustertest.redisson.clusterdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ClusterdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClusterdemoApplication.class, args);
	}
}
