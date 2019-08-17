package com.pm.mss.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ResourceLoader;

public class PmMssSpringApplication extends SpringApplication {

    private final static Logger LOG= LoggerFactory.getLogger(PmMssSpringApplication.class);

    public PmMssSpringApplication(Class... primarySources) {
      super(primarySources);
    }

    public PmMssSpringApplication(ResourceLoader resourceLoader, Class... primarySources) {
        super(resourceLoader,primarySources);
    }

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class[]{primarySource}, args);
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return (new PmMssSpringApplication(primarySources)).run(args);
    }

    @Override
    protected void afterRefresh(ConfigurableApplicationContext context, ApplicationArguments args) {
        LOG.info("afterRefresh:{}","ffffff");
       // context.publishEvent(null);
    }
}
