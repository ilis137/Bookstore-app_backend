package com.example.bookstorebackendappcfp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@Slf4j
public class BookstoreBackendappCfpApplication {

    public static void main(String[] args) {

        ApplicationContext context=SpringApplication.run(BookstoreBackendappCfpApplication.class, args);
        log.info("Bookstore App Started in {} Environment", context.getEnvironment().getProperty("environment"));
        log.info("Bookstore DB User is {} Environment", context.getEnvironment().getProperty("spring.datasource.username"));
    }

}
