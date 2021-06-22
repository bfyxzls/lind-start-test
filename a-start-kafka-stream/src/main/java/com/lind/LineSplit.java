package com.lind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LineSplit {

    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication(LineSplit.class);
        springApplication.run(args);
    }
}
