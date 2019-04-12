package com.pm.mss.server.config;

import com.pm.mss.server.config.tomcat.TomcatServletWebServerFactory1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig{

    @Bean
    TomcatServletWebServerFactory1 tomcat(){
     return  new TomcatServletWebServerFactory1();
    }

}
