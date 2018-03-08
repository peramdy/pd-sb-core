package com.pd.spring.redis;

import java.lang.annotation.*;

/**
 * @author pd 2018/2/7.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PdDelCache {
    String value();
}
