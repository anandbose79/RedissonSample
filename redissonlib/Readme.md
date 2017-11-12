# Redisson Client Library
   This library contains useful utilities to connect to Redis cluster with multiple master/ slaves . Also the lirary supports sharding (This is automatic ) . Also the library has abstracted the following functionalities:

   1. CacheGet
   2. CachePut
   3. CacheEvict

## Instructions on using the library

   The libraty is uploaded in artifactory and has public access. The following snippet needs to be added to the POM file.
   1. Add the library dependency
   ```
   <dependency>
     <groupId>com.anand.redisson</groupId>
     <artifactId>redissonlib</artifactId>
     <version>0.0.1-SNAPSHOT</version>
   </dependency>
   ```
   2. Add the repository for Artifactory
   ```
   <repositories>
       <repository>
         <id>snapshots</id>
         <name>anandsartifactory-snapshots</name>
         <url>https://anandsartifactory.jfrog.io/anandsartifactory/libs-snapshot-local</url>
       </repository>
   </repositories>
   ```
## How to use the redisson library

  The prerequisite for using the library is to create the client. The client connection can be created by using a YML file with Redisson connection parameters

  1. Sample YML file is below

  ```
  replicatedServersConfig:
  idleConnectionTimeout: 10000
  pingTimeout: 1000
  connectTimeout: 10000
  timeout: 3000
  retryAttempts: 3
  retryInterval: 1500
  reconnectionTimeout: 3000
  failedAttempts: 3
  password: null
  subscriptionsPerConnection: 5
  clientName: null
  loadBalancer: !<org.redisson.connection.balancer.RoundRobinLoadBalancer> {}
  subscriptionConnectionMinimumIdleSize: 1
  subscriptionConnectionPoolSize: 50
  slaveConnectionMinimumIdleSize: 10
  slaveConnectionPoolSize: 64
  masterConnectionMinimumIdleSize: 10
  masterConnectionPoolSize: 64
  readMode: "SLAVE"
  subscriptionMode: "SLAVE"
  nodeAddresses:
  - "redis://127.0.0.1:2812"
  - "redis://127.0.0.1:2815"
  - "redis://127.0.0.1:2813"
  scanInterval: 1000
  threads: 0

  ```
  The sample client connection code is below . This needs to be declared as a bean in all the client implementation

  ```
  Config config = Config.fromYAML(new File("/Users/anandbose/Redis/redis-local-cluster.yml"));
  RedissonClient client = Redisson.create(config);

  ```

## Cache usage Instructions
  There are custom annotations created in the library
### @RCachePut

      @RCachePut annotation is used to create/update an object in Cache. This implementation takes the object , and stores it against the key passed. The parameters are:

      1.cacheName - Unique Name for the cache space. This is a mandatory field
      2.key - Unique key for the specific object . Key needs to be specified as a parameter in the method. Also key can be specified as a property within the object. Key cannot be present as a  hardcoded value. This is a mandatory field
      eg : key="#data.cachekey" method signature would be
      public voif cacheData(ExampleData data)
      The key will be extracted from the object , where cachekey is a field in the object.
      3.ttl - Time to live. This can be specified in the yml file . For minutes end with M eg;(10M - means 10 minutes) , hours  end with H and for days end with D . To use a ttl specified in the yml config file , prefix the parameter with a # . Eg : ttl="#redis.ttl.test" where redis.ttl.test is the propert name in yml.
### @RCacheGet

      @RCacheGet annotation is used to retreive the object from Cache. This is a required field. The parameter for this is "key" .Key needs to be specified as a parameter in the method and is expected to be of String type.

### @RCacheEvict

      @RCacheEvict annotation is used to clear data in specific cachenames. The parameters are
      1. cacheName
            Unique Name for the cache space. This is a mandatory field
      2. scope
           This is the scope for eviction.This is a mandatory field. There are 2 scopes currently defined
           * ALL
                - This will clear all the keys in the cache space
           * KEY
                - This will clear a specific key in the cache space
      3. key
           This is valid only if scope is set to "KEY". Key needs to be specified as a parameter in the method and is expected to be of String type.
