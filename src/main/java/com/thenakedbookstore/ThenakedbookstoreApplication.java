package com.thenakedbookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableScheduling
@SpringBootApplication
public class ThenakedbookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThenakedbookstoreApplication.class, args);
    }

}
