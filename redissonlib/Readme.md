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
