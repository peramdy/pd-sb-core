package com.pd.spring;

import com.pd.spring.modelattribute.IpResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author pd 2018/3/9.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(ipResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public IpResolver ipResolver() {
        return new IpResolver();
    }

}
