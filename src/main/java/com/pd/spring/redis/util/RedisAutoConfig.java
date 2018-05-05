package com.pd.spring.redis.util;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.pd.spring.utils.PdConstants.PD_PREFIX;

/**
 * @author peramdy
 */
@Configuration
@EnableConfigurationProperties(value = {RedisProperties.class})
@ConditionalOnProperty(prefix = PD_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class RedisAutoConfig {

}
