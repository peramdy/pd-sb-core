package com.pd.spring.redis.aspect;

import com.pd.spring.redis.PdCache;
import com.pd.spring.redis.PdDelCache;
import com.pd.spring.redis.util.JedisUtils;
import com.pd.spring.utils.ParseSpringEl;
import com.pd.spring.utils.SerializeUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author pd 2018/3/8.
 */
@Aspect
@Component("pdCacheAspect")
public class PdAspect {


    private static Logger logger = LoggerFactory.getLogger(PdAspect.class);

    @Autowired
    private JedisUtils jedisUtils;

    private static final byte[] NOT_FOUNT = "404".getBytes();
    private static final Integer EMPTY_TIME = 10;

    /**
     * 获取缓存
     *
     * @param joinPoint
     * @param pdCache
     * @return
     */
    @Around(value = "@annotation(pdCache)")
    public Object pdCache(ProceedingJoinPoint joinPoint, PdCache pdCache) {
        Jedis jedis = null;
        try {
            if (pdCache.value() == null) {
                return joinPoint.proceed();
            }
            String key = getKey(pdCache.value(), joinPoint);
            logger.info("key --> " + key);
            if (key == null) {
                return joinPoint.proceed();
            }
            int expire = pdCache.expire();
            jedis = jedisUtils.createJedis();
            byte[] keyBytes = SerializeUtils.serializeObject(key);
            byte[] value = jedis.get(keyBytes);
            /**redis key的存活时间**/
            Long existSeconds = jedis.ttl(keyBytes);
            logger.info("set key expire time --> " + expire + "s");
            logger.info("key exist time --> " + existSeconds + "s");
            logger.info("get redis value --> " + value);
            if (value == null) {
                Object currentValue = joinPoint.proceed();
                if (currentValue != null) {
                    byte[] valueBytes = SerializeUtils.serializeObject(currentValue);
                    logger.info("get byte value --> " + valueBytes);
                    if (expire > 0 && existSeconds < 0) {
                        logger.info("set byte value --> " + valueBytes);
                        jedis.setex(keyBytes, expire, valueBytes);
                    } else {
                        jedis.set(keyBytes, valueBytes);
                    }
                } else {
                    /***数据库查询为null时每隔10s钟查询一次***/
                    jedis.setex(keyBytes, EMPTY_TIME, NOT_FOUNT);
                    logger.info("sql query empty");
                }
                return currentValue;
            } else {
                /***获取值是否查询是为空***/
                if (Arrays.equals(value, NOT_FOUNT)) {
                    logger.info("redis query empty");
                    return null;
                }

                /***设置过期时间***/
                if (expire > 0 && existSeconds < 0) {
                    logger.info("set key expire time (value not null) --> " + expire + "s");
                    jedis.expire(keyBytes, expire);
                }
                /*** 获取返回类型 ***/
                Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();
                Object reCacheValue = SerializeUtils.deserialize(value, returnType);
                return reCacheValue;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }


    /**
     * 删除缓存
     *
     * @param joinPoint
     * @param pdDelCache
     */
    @Around(value = "@annotation(pdDelCache)")
    public Object pdDelCache(ProceedingJoinPoint joinPoint, PdDelCache pdDelCache) {
        Jedis jedis = null;
        try {
            if (pdDelCache.value() == null) {
                return joinPoint.proceed();
            }
            String key = getKey(pdDelCache.value(), joinPoint);
            if (key == null) {
                return joinPoint.proceed();
            }
            byte[] keyBytes = SerializeUtils.serializeObject(key);
            jedis = jedisUtils.createJedis();
            jedis.del(keyBytes);
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 解析spring el 表达式 ****
     * getKey
     *
     * @param keyName
     * @param joinPoint
     * @return
     */
    private static String getKey(String keyName, ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        String[] parametersName = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
        return ParseSpringEl.getKey(keyName, parametersName, args);
    }

}
