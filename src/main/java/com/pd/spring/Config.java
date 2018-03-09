package com.pd.spring;

import com.pd.spring.modelattribute.ArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author pd 2018/3/9.
 */
@Configuration
public class Config extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(argumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public ArgumentResolver argumentResolver() {
        return new ArgumentResolver();
    }
}
