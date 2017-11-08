package com.anand.test.clustertest.redisson.clusterdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anand.test.clustertest.redisson.clusterdemo.models.ExampleData;
import com.anand.test.clustertest.redisson.clusterdemo.models.SuccessResponse;
import com.anand.test.clustertest.redisson.clusterdemo.repo.DataRepo;

@RestController
public class MyController {
	@Autowired
	private DataRepo repo;
	@RequestMapping(value = "/addcustomer",consumes = {"application/json"},
	produces= {"application/json"}	,	 method = RequestMethod.POST)	
	public @ResponseBody SuccessResponse setData(@RequestBody ExampleData input)
	{
		input.setCachekey(String.valueOf(input.getId()));
		repo.cacheData(input);
		SuccessResponse resp = new SuccessResponse();
		resp.setSuccessCode(200);
		return (resp);
	}
	
	@RequestMapping(value = "/customer",produces = {"application/json"},
			 method = RequestMethod.GET)	
	public @ResponseBody SuccessResponse getData(@RequestParam(value="id",required=true) String id)
	{
		ExampleData data = repo.fetchData(id);
		SuccessResponse resp = new SuccessResponse();
		resp.setSuccessCode(200);
		resp.setReponseData(data);
		return (resp);
	}
	
	@RequestMapping(value = "/delcustomer",produces = {"application/json"},
			 method = RequestMethod.GET)	
	public @ResponseBody SuccessResponse clearData(@RequestParam(value="id",required=true) String id)
	{
		if (id.equals("ALL"))
		{
			repo.delAllKeys();
		}
		else
		{
		 repo.delKey(id);
		}
		SuccessResponse resp = new SuccessResponse();
		resp.setSuccessCode(200);
		//resp.setReponseData(data);
		return (resp);
	}
}
