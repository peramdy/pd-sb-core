package com.pd.spring.modelattribute;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * @author pd 2018/3/9.
 */
@Aspect
@Component("pdIpAspect")
public class PdAspect {


    @Pointcut(value = "execution(@IP * *.*(..))")
    public void setCacheRedis() {
    }


    @Around("setCacheRedis()")
    public Object ip(ProceedingJoinPoint joinPoint) {
        System.out.println("IP annotation");
        return "haha";
    }


}
