package com.tieto.service.monitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
	String version = "1.0.0";
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tieto.service.monitoring"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo(version));
    }

    private ApiInfo apiInfo(String version) {
        return new ApiInfo("Monitoring service", "REST API for monitoring uses.\r\n Tieto", version, "Terms of service",
                new Contact("Tieto", "http://www.tieto.com",
                        "Tieto, info@tieto.com"),
                "Tieto", "www.tieto.com", new ArrayList<>());
    }
}


