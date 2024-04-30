package com.hcl.carehe.common.config.redis;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class JedisConfigurationProperties {
	@Value("${spring.redis.jedis.pool.connect-timeout-millis}")
	private Integer connectTimeout;
	
	@Value("${spring.redis.jedis.pool.max-total-count}")
	private Integer maxActiveCount;
	
	@Value("${spring.redis.jedis.pool.max-idle-count}")
	private Integer maxIdleCount;
	
	@Value("${spring.redis.jedis.pool.min-idle-count}")
	private Integer minIdleCount;
}
