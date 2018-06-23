/*package edu.ecnu.ykyl.service.util;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class GetRedis {

	public static Jedis getRedisConn() {
		final String HOST = "58.198.176.191";
		final int PORT = 6379;
		Jedis jedis = new Jedis(HOST, PORT);

		jedis.auth("123456");

		return jedis;
	}

	@Test
	public void getRedis() {
		System.out.println(GetRedis.getRedisConn().get("11"));
	}

}
*/