package org.andrey.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
public class App {
    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }
}