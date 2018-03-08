package com.pd.spring.redis;

import java.lang.annotation.*;

/**
 * @author pd 2018/3/8.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PdCache {

    String value() default "";

    int expire() default 0;
}
