package com.anand.test.clustertest.redisson.clusterdemo.repo;


import org.springframework.stereotype.Repository;

import com.anand.redisson.RCacheEvict;
import com.anand.redisson.RCacheGet;
import com.anand.redisson.RCachePut;
import com.anand.test.clustertest.redisson.clusterdemo.models.ExampleData;

@Repository
public class DataRepo {

	
	@RCachePut(cacheName = "test", key = "#data.cachekey" , ttl="#redis.ttl.test")
	public ExampleData cacheData(ExampleData data)
	{

		return data;
	}
	
	@RCacheGet(cacheName = "test",key = "key")
	public ExampleData fetchData(String key)
	{

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
