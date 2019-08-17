package com.pm.mss.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
        scanBasePackages={"com.pm.mss"})
public class PmMssApplication {

    public static void main(String[] args) {
        PmMssSpringApplication.run(PmMssApplication.class, args);
//        System.out.println(Thread.getAllStackTraces());
    }

}
