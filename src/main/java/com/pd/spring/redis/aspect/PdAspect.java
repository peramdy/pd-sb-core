package com.pd.spring.redis.aspect;

import com.pd.spring.redis.PdCache;
import com.pd.spring.redis.PdDelCache;
import com.pd.spring.redis.uitil.JedisUtils;
import com.pd.spring.utils.ParseSpringEl;
import com.pd.spring.utils.SerializeUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

/**
 * @author pd 2018/3/8.
 */
@Aspect
@Component("pdCacheAspect")
public class PdAspect {

    @Autowired
    private JedisUtils jedisUtils;

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
            if (key == null) {
                return joinPoint.proceed();
            }
            int expire = pdCache.expire();
            jedis = jedisUtils.createJedis();
            byte[] keyBytes = SerializeUtils.serializeObject(key);
            byte[] value = jedis.get(keyBytes);
            if (value == null) {
                Object currentValue = joinPoint.proceed();
                if (currentValue != null) {
                    byte[] valueBytes = SerializeUtils.serializeObject(currentValue);
                    if (expire > 0) {
                        jedis.setex(keyBytes, expire, valueBytes);
                    } else {
                        jedis.set(keyBytes, valueBytes);
                    }
                }
                return currentValue;
            } else {
                /***设置过期时间***/
                if (expire > 0) {
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
