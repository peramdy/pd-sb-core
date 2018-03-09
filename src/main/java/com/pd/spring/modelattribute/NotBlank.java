package com.pd.spring.modelattribute;

import java.lang.annotation.*;

/**
 * @author pd 2018/3/9.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotBlank {
}
