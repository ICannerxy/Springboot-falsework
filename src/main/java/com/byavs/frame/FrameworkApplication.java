package com.byavs.frame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FrameworkApplication {
    private static final Logger logger = LoggerFactory.getLogger(FrameworkApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FrameworkApplication.class, args);
        logger.info("Springboot project is run success");
    }

}
