package com.anand.test.clustertest.redisson.clusterdemo.models;

import java.util.Set;

public class CacheKeyCollection {
 String cachename;
 Set<String> keys;
@Override
public String toString() {
	return "CacheKeyCollection [cachename=" + cachename + ", keys=" + keys + "]";
}
public String getCachename() {
	return cachename;
}
public void setCachename(String cachename) {
	this.cachename = cachename;
}
public Set<String> getKeys() {
	return keys;
}
public void setKeys(Set<String> keys) {
	this.keys = keys;
}
}
