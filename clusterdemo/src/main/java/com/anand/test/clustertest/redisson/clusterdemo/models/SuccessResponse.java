package com.anand.test.clustertest.redisson.clusterdemo.models;

public class SuccessResponse {

	 int SuccessCode;
	 ExampleData reponseData;
	public int getSuccessCode() {
		return SuccessCode;
	}
	public void setSuccessCode(int successCode) {
		SuccessCode = successCode;
	}
	public ExampleData getReponseData() {
		return reponseData;
	}
	public void setReponseData(ExampleData reponseData) {
		this.reponseData = reponseData;
	}
	@Override
	public String toString() {
		return "SuccessResponse [SuccessCode=" + SuccessCode + ", reponseData=" + reponseData + "]";
	}
}
