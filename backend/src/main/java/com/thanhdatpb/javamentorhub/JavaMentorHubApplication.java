package com.thanhdatpb.javamentorhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JavaMentorHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaMentorHubApplication.class, args);
    }

}
