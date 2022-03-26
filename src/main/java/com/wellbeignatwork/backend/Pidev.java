package com.wellbeignatwork.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Pidev {

    public static void main(String[] args) {
        SpringApplication.run(Pidev.class, args);
    }

}
