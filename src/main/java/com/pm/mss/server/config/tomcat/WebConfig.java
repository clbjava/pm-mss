package com.pm.mss.server.config.tomcat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class WebConfig {

    /*@Bean
    public CorsRegistry addCorsRegistry() {
        CorsRegistry registry = new CorsRegistry();
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1")
                .allowedMethods("PUT", "DELETE", "GET", "POST")
                .allowedHeaders("header1", "header2", "header3")
                .exposedHeaders("header1", "header2")
                .allowCredentials(false).maxAge(3600);
        return registry;
    }*/

}
