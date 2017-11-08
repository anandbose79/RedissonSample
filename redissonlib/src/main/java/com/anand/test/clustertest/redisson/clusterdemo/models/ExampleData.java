package com.anand.test.clustertest.redisson.clusterdemo.models;

import java.io.Serializable;

public class ExampleData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String cachekey;
	public String custName;
	public String custAge;
	public int id;
	@Override
	public String toString() {
		return "ExampleData [cachekey=" + cachekey + ", custName=" + custName + ", custAge=" + custAge + ", id=" + id
				+ "]";
	}
	public String getCachekey() {
		return cachekey;
	}
	public void setCachekey(String cachekey) {
		this.cachekey = cachekey;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustAge() {
		return custAge;
	}
	public void setCustAge(String custAge) {
		this.custAge = custAge;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
