package com.meklund.service.monitoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations(getStaticLocations());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/actuator/info");
    }

    private String[] getStaticLocations() {

        String[] result = new String[5];
        result[0] = "/";
        result[1] = "classpath:/META-INF/resources/";
        result[2] = "classpath:/resources/";
        result[3] = "classpath:/static/";
        result[4] = "classpath:/public/";

        return result;
    }
}
