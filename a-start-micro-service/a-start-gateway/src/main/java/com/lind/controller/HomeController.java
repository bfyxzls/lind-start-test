package com.lind.controller;

import com.lind.feign.OauthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    OauthClient oauthClient;

    @GetMapping("index")
    public String index() {
        return oauthClient.a();
    }
}
