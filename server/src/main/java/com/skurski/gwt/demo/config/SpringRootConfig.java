package com.skurski.gwt.demo.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableTransactionManagement
@Import(DatabaseConfig.class)
@ComponentScan(basePackages={"com.skurski.gwt.demo"},
        excludeFilters={
                @ComponentScan.Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
        })
public class SpringRootConfig {
}