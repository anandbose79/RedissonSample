package com.anand.test.clustertest.redisson.clusterdemo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.anand.test.clustertest.redisson.clusterdemo.models.CacheKeyCollection;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RcustomAspect {
 @Autowired
 RedissonClient client;
 @Autowired
 private Environment env;
 private String ttl="";
 @Pointcut("@annotation(cacheput)")
 public void callAt(RCachePut cacheput) {
 }
 
 @Pointcut("@annotation(cacheget)")
 public void getAt(RCacheGet cacheget) {
 }
 
 @Pointcut("@annotation(cacheevict)")
 public void deleteAt(RCacheEvict cacheevict) {
 }
 
 @Around("callAt(cacheput)")
 public Object cachePut(ProceedingJoinPoint joinPoint,RCachePut cacheput) throws Throwable
 {
	 //write the object to Cache...
	 Object[] Arguments = joinPoint.getArgs();
	 //there should be the object to put to Cache , using reflection
     MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
     String[] Names = methodSignature.getParameterNames();
     String Key = cacheput.key();
	  String cacheName = cacheput.cacheName();
	  String ttlVal = cacheput.ttl();
	  if (ttlVal.startsWith("#"))
		  
		  	ttl = env.getProperty(ttlVal.substring(1));
	  else
		  	ttl=ttlVal;

	  long time = 7;
      TimeUnit timeUnit = TimeUnit.DAYS;
	  
	  if (ttl!=null)
		  time = Long.valueOf(ttl.substring(0, ttl.length()-1));
	  if(ttl.contains("D"))
	  {
		  //Days
		  timeUnit = TimeUnit.DAYS;
		  
	  }else if (ttl.contains("H")) 
	  {
		  timeUnit = TimeUnit.HOURS;
	  } else if(ttl.contains("M"))
	  {
		  timeUnit = TimeUnit.MINUTES;
	  }
 
     String [] splitKeys = Key.split("\\.");
     int keyIndex = 0;
     for (int i=0;i<Names.length;i++ )
     {
    	 	//check if name matches key
    	     if (("#"+Names[i]).equals(splitKeys[0]))
    	     {
    	    	 	keyIndex = i;
    	    	 	break;
    	     }
     }
    	
	 Object obj = Arguments[keyIndex];
	 Field field = obj.getClass().getField(splitKeys[1]);
	 String cachekey = (String)field.get(obj);
	 RBucket<Object> bucket = client.getBucket(cacheName+"-"+cachekey);
		bucket.set(obj);
		bucket.expire(time, timeUnit);
	 
	 //now add this key to the list of cachenames
	 RBucket<CacheKeyCollection> cachecoll = client.getBucket(cacheName);
	 CacheKeyCollection objcoll = cachecoll.get();
	 if (objcoll == null ||objcoll.getCachename() == null)
	 {
		  objcoll = new CacheKeyCollection();
		  objcoll.setCachename(cacheName);
		  Set<String> s = new HashSet<String>();
		  s.add(cachekey);
		  objcoll.setKeys(s);
		  
	 }
	 else
	 {
		 Set<String> s= objcoll.getKeys();
		 s.add(cachekey);
		 objcoll.setKeys(s);
	 }
	 cachecoll.set(objcoll);
	 //proceed to finish the operation
	 return joinPoint.proceed();
 }
 
 
 @Around("getAt(cacheget)")
 public Object cacheGet(ProceedingJoinPoint joinPoint,RCacheGet cacheget) throws Throwable
 {
	 //write the object to Cache...
	 Object[] Arguments = joinPoint.getArgs();
     MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
     String[] Names = methodSignature.getParameterNames();
     String Key = cacheget.key();
     String cacheName = cacheget.cacheName();
     
     int keyIndex = 0;
     for (int i=0;i<Names.length;i++ )
     {
    	 	//check if name matches key

    	     if ((Names[i]).equals(Key))
    	     {
    	    	 	keyIndex = i;
    	    	 	break;
    	     }
     }
    	
	 String cachekey = cacheName+"-"+(String)Arguments[keyIndex];

	 RBucket<Object> bucket = client.getBucket(cachekey);
	 return bucket.get();	

 }
 
 
 //Evict from Cache
 
 @Around("deleteAt(cacheevict)")
 public Object cacheEvict(ProceedingJoinPoint joinPoint,RCacheEvict cacheevict) throws Throwable
 {
	 //write the object to Cache...
	 Object[] Arguments = joinPoint.getArgs();
	 //there should be the object to put to Cache , using reflection
     MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

     String[] Names = methodSignature.getParameterNames();
     String Key = cacheevict.key();
	  String cacheName = cacheevict.cacheName();
	  

     int keyIndex = 0;
     for (int i=0;i<Names.length;i++ )
     {
    	     if ((Names[i]).equals(Key))
    	     {
    	    	 	keyIndex = i;
    	    	 	break;
    	     }
     }
    	

	 //depending on scope clear the keys
	 String scope = cacheevict.scope();
	 RBucket<CacheKeyCollection> MainBucket = client.getBucket(cacheName);
	 if (!MainBucket.isExists()) return null;
	 Set<String> s = MainBucket.get().getKeys();
	 if (scope.equals("ALL"))
	 {
		 //delete all keys in the namespace
		 //iterate through all keys

		 s.forEach(key -> {
			 RBucket<Object> bucket = client.getBucket(cacheName+"-"+key);
			 bucket.delete();
		 
		 });
		 MainBucket.delete();
	 }
	 else
	 {
		 Object obj = Arguments[keyIndex];
		 String cachekey = (String)(obj);
		 RBucket<Object> bucket = client.getBucket(cacheName+"-"+cachekey);
		 bucket.delete();
		 s.remove(cachekey);
		 CacheKeyCollection coll = MainBucket.get();
		 coll.setKeys(s);
		 MainBucket.set(coll);

		 
	 }
	 


	 return joinPoint.proceed();
 }
 
}
