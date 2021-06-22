package com.lind.oauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {
    @GetMapping("/index")
    public String index(@RequestParam String prams) {
        return "hello index.";
    }

    @GetMapping("/test/a")
    public String a() {
        log.info("test/a request");
        return "hello a.";
    }
}
