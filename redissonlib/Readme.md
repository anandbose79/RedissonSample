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
