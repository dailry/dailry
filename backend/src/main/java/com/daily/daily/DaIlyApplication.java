package com.daily.daily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DaIlyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DaIlyApplication.class, args);
    }
}
