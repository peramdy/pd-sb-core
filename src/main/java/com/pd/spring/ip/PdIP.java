package com.pd.spring.ip;

import java.lang.annotation.*;

/**
 * @author pd 2018/3/9.
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PdIP {
}
