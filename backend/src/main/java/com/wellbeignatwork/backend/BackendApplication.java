package com.wellbeignatwork.backend;

import com.wellbeignatwork.backend.service.Forum.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import javax.annotation.Resource;

@EnableSwagger2
@SpringBootApplication
@EnableScheduling
public class BackendApplication implements CommandLineRunner {
    @Resource
    FileStorageService storageService;
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
    @Override
    public void run(String... arg) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }


}
