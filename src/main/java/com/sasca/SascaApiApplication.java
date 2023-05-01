package com.sasca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SascaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SascaApiApplication.class, args);
    }

}
