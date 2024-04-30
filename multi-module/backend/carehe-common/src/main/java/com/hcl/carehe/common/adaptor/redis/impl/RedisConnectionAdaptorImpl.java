package com.hcl.carehe.common.adaptor.redis.impl;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.hcl.carehe.common.adaptor.redis.RedisConnectionAdaptor;


@Service
public class RedisConnectionAdaptorImpl implements RedisConnectionAdaptor{
	
	
	@Autowired
	private RedisTemplate<String, String> redisTemplateObject;

	@Override
	public void set(String key, String object) {
		redisTemplateObject.opsForValue().set(key, object);
	}

	@Override
	public void setExpired(String key, String object, long timeout, TimeUnit unit) {
		redisTemplateObject.opsForValue().set(key, object, timeout, unit);
	}

	@Override
	public String get(String key) {
		return (String) redisTemplateObject.opsForValue().get(key);
	}

	@Override
	public String getString(String key) {
		return (String) get(key);
	}

	@Override
	public boolean hasKey(String key) {
		return redisTemplateObject.hasKey(key);
	}

	@Override
	public Set<String> keys(String keyPattern) {
		return redisTemplateObject.keys(keyPattern);
	}

	@Override
	public Boolean removeKey(String key) {
		return redisTemplateObject.delete(key);
		
	}
	

}
