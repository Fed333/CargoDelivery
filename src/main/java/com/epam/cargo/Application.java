package com.epam.cargo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry launch point of the Spring application.<br>
 * Raises the spring boot application, sets spring context.<br>
 * @author Roman Kovalchuk
 * @see SpringApplication
 * @version 1.0
 * */
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}