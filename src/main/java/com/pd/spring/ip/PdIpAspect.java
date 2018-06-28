package com.pd.spring.ip;

import com.pd.spring.utils.IpUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @author pd 2018/3/9.
 */
@Deprecated
@Aspect
@Component
public class PdIpAspect {


    /*@Pointcut(value = "execution(@com.pd.spring.modelattribute.IP * com.pd.spring.test.controller.*(..))")
    public void pointcut() {
    }*/


    @Before("@annotation(pdIP)")
    public Object parseIp(PdIP pdIP) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return IpUtil.parseIp(request);
    }
}
