package com.lind.http2.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="demo",url = "http://localhost:8088/")
public interface SelfClient {
    @GetMapping("send2")
    String send2();

    @GetMapping("send3")
    String send3();

    @GetMapping("send4")
    String send4();

    @GetMapping("send5")
    String send5();
}
