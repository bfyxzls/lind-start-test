package com.lind;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableFeignClients
public class GatewayApplication {
    @Value("${auth:lind}")
    private String auth;

    @Value("${email:lind@sina.com}")
    private String email;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);

    }

    @GetMapping("hello")
    public ResponseEntity hello() {
        return ResponseEntity.ok(auth);
    }
}
