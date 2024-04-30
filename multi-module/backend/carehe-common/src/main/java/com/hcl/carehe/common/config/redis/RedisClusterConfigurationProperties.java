package com.hcl.carehe.common.config.redis;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterConfigurationProperties {
	
	private List<String> nodes;

}
