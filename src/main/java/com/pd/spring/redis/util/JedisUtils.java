package com.pd.spring.redis.util;

import com.pd.spring.exception.PdJedisConnectionException;
import com.pd.spring.redis.builder.JedisBuilder;
import com.pd.spring.redis.builder.JedisClusterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author peramdy on 2018/2/7.
 */

@Component
public class JedisUtils {

    @Autowired
    private RedisProperties redisConfig;

    /**
     * create cluster
     *
     * @return
     * @throws Exception
     */
    public JedisCluster createJedisCluster() {
        JedisClusterBuilder jedisClusterBuilder = JedisClusterBuilder.getInstance();
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisConfig.getMaxTotal());
        poolConfig.setMinIdle(redisConfig.getMinIdle());
        poolConfig.setMaxIdle(redisConfig.getMaxIdle());
        jedisClusterBuilder.setAddressConfigs(redisConfig.getAddresses());
        jedisClusterBuilder.setGenericObjectPoolConfig(poolConfig);
        jedisClusterBuilder.setMaxTotal(redisConfig.getMaxTotal());
        jedisClusterBuilder.setTimeout(redisConfig.getTimeout());
        try {
            return jedisClusterBuilder.build();
        } catch (Exception e) {
            try {
                throw new PdJedisConnectionException(e);
            } catch (PdJedisConnectionException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }


    /**
     * create single
     *
     * @return
     * @throws Exception
     */
    public Jedis createJedis() {
        Jedis jedis = null;
        try {
            JedisBuilder jedisBuilder = JedisBuilder.getInstance();
            jedisBuilder.setHost(redisConfig.getIp());
            jedisBuilder.setPort(redisConfig.getPort());
            jedisBuilder.setTimeout(redisConfig.getTimeout());
            jedis = jedisBuilder.build();
        } catch (Exception e) {
            try {
                throw new PdJedisConnectionException(e);
            } catch (PdJedisConnectionException e1) {
                e1.printStackTrace();
            }
        }
        return jedis;
    }


}
