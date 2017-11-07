package com.anand.test.clustertest.redisson.clusterdemo.repo;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.anand.test.clustertest.redisson.clusterdemo.aop.RCacheEvict;
import com.anand.test.clustertest.redisson.clusterdemo.aop.RCacheGet;
import com.anand.test.clustertest.redisson.clusterdemo.aop.RCachePut;
import com.anand.test.clustertest.redisson.clusterdemo.models.ExampleData;

@Repository
public class DataRepo {

	@Autowired
	private RedissonClient client;
	//Put Data To Cache
	
	@RCachePut(cacheName = "test", key = "#data.cachekey" , ttl="#redis.ttl.test")
	public ExampleData cacheData(ExampleData data)
	{

		return data;
	}
	
	@RCacheGet(cacheName = "test",key = "key")
	public ExampleData fetchData(String key)
	{
		//RBucket<ExampleData> bucket = client.getBucket(key);
		//return bucket.get();
		return null;

	}
	
	@RCacheEvict(cacheName = "test" , scope = "KEY" , key ="testkey")
	public void delKey(String testkey)
	{
	}
	@RCacheEvict(cacheName = "test" , scope = "ALL" )
	public void delAllKeys()
	{
	}	
	
}
