package com.anand.test.clustertest.redisson.clusterdemo.configuration;

import java.io.File;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RedisonCache {
 public @Bean RedissonClient createRedissonBean() throws Exception
 {
	 Config config = Config.fromYAML(new File("/Users/anandbose/tmob/Redis/redis-local-cluster.yml"));
	 RedissonClient client = Redisson.create(config);
	 return client;
 }
 
 
}
