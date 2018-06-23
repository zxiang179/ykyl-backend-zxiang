package edu.ecnu.ykyl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.service.redis.service.IRedisService;


@RestController
public class ExampleController {

	
	@Autowired
	private IRedisService redisService;
	
	
	@RequestMapping("/redis/set")
	public String redisSet(@RequestParam("value")String value){
		redisService.set("name", value);
		return "set success";
	}
	
	@RequestMapping("/redis/get")
	public String redisGet(){
		String name = redisService.get("name");
		return name;
	}
	
}
