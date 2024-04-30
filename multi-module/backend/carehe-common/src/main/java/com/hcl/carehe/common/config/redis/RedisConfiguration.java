package com.hcl.carehe.common.config.redis;


import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
public class RedisConfiguration {
	
	
	@Autowired
	private RedisClusterConfigurationProperties redisClusterConfigurationProperties;
	
	@Autowired
	private JedisConfigurationProperties jedisConfigurationProperties;
	
	
	
	@Bean
    JedisConnectionFactory jedisConnectionFactory() {
		log.debug("RedisConfiguration.jedisConnectionFactory maxActiveCount => {}" ,jedisConfigurationProperties.getMaxActiveCount());
		log.debug("RedisConfiguration.jedisConnectionFactory maxIdleCount => {}",jedisConfigurationProperties.getMaxIdleCount());
		log.debug("RedisConfiguration.jedisConnectionFactory minIdleCount => {}", jedisConfigurationProperties.getMinIdleCount());
		log.debug("RedisConfiguration.jedisConnectionFactory connectTimeout => {}", jedisConfigurationProperties.getConnectTimeout());
		log.debug("RedisConfiguration.jedisConnectionFactory nodes => {}", redisClusterConfigurationProperties.getNodes());
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(jedisConfigurationProperties.getMaxActiveCount());
        poolConfig.setMaxIdle(jedisConfigurationProperties.getMaxIdleCount());
        poolConfig.setMinIdle(jedisConfigurationProperties.getMinIdleCount());

        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnReturn(false);

        JedisClientConfiguration.JedisClientConfigurationBuilder clientConfig = JedisClientConfiguration.builder();
        clientConfig.connectTimeout(Duration.ofMillis(jedisConfigurationProperties.getConnectTimeout()));
        clientConfig.usePooling().poolConfig(poolConfig);

        return new JedisConnectionFactory(
                new RedisClusterConfiguration(redisClusterConfigurationProperties.getNodes()),
                clientConfig.build());
    }

	
	@Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(new ObjectMapper()));
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
}