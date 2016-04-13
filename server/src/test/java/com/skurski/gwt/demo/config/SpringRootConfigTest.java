package com.skurski.gwt.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by pskurski on 3/23/2016.
 */
@Configuration
@Import(DatabaseConfigTest.class)
@ComponentScan(basePackages={"com.skurski.gwt.demo"},
        excludeFilters={
                @ComponentScan.Filter(type= FilterType.ANNOTATION, value=EnableWebMvc.class)
        })
public class SpringRootConfigTest {

}
