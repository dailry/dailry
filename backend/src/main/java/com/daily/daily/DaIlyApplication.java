package com.daily.daily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DaIlyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DaIlyApplication.class, args);
    }
}
