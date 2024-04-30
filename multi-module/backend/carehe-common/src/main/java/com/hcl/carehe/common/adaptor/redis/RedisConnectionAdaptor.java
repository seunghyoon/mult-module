package com.hcl.carehe.common.adaptor.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisConnectionAdaptor {
	
	public void set(String key, String object);
	
	public void setExpired(String key, String object, long timeout, TimeUnit unit);
	
	
	public String get(String key);
	
	public String getString(String key);
	
	
	public boolean hasKey(String key);
	
	public Set<String> keys(String keyPattern);
	
	
	public Boolean removeKey(String key);
}
