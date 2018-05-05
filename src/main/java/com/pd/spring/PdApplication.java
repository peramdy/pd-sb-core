package com.pd.spring;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import static com.pd.spring.utils.PdConstants.PD_PACKAGE_PREFIX;


/**
 * @author pd 2018/3/8.
 */
@SpringBootApplication
@ComponentScan(value = PD_PACKAGE_PREFIX)
public class PdApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(PdApplication.class);
        application.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.bannerMode(Banner.Mode.CONSOLE).sources(PdApplication.class);
    }


}
