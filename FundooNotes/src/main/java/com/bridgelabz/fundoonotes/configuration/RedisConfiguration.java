package com.bridgelabz.fundoonotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class RedisConfiguration {
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
	    return new JedisConnectionFactory();
	}
	
	
}
