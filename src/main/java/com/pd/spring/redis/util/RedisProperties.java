package com.pd.spring.redis.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.pd.spring.utils.PdConstants.PD_REDIS_PREFIX;

/**
 * @author pd 2018/3/8.
 */
@Component
@ConfigurationProperties(prefix = PD_REDIS_PREFIX)
public class RedisProperties {

    @Value("localhost:6379")
    private String addresses;
    @Value("120")
    private int timeout;
    @Value("100")
    private int maxTotal;
    @Value("50")
    private int maxIdle;
    @Value("10")
    private int minIdle;
    @Value("localhost")
    private String ip;
    @Value("6379")
    private int port;

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
