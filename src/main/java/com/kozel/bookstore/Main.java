package com.kozel.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

/**
 * The main class for the bookstore application.
 * This class serves as the entry point for running the Spring Boot application.
 * The {@code @SpringBootApplication} annotation combines several Spring annotations,
 * including {@code @Configuration}, {@code @EnableAutoConfiguration}, and {@code @ComponentScan},
 * to bootstrap the application.
 * <p>
 * The {@code exclude} attribute is used to disable the default Spring Boot error handling mechanism
 * provided by {@link ErrorMvcAutoConfiguration}, allowing for custom error handling.
 */
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class Main {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
