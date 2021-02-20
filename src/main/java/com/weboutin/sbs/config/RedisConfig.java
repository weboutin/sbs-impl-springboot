package com.weboutin.sbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class RedisConfig {

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    // @Bean
    // public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    //     return new StringRedisTemplate(redisConnectionFactory);
    // }
}
