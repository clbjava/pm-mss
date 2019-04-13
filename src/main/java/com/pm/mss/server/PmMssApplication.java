package com.pm.mss.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.pm.mss"},lazyInit=false)
public class PmMssApplication {

    public static void main(String[] args) {
        SpringApplication.run(PmMssApplication.class, args);
//        System.out.println(Thread.getAllStackTraces());
    }

}
