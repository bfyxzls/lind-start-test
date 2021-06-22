package com.lind.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "lind-start-oauth")
public interface OauthClient {
    @GetMapping("/test/a")
    String a();
}
