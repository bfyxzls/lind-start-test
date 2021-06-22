package com.lind.hot.deploy;

import com.lind.spi.ProviderFactory;
import com.lind.spi.SpiFactory;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class RunApplication extends SpringBootServletInitializer {


    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);
        SpiFactory.watchDir("d:\\jar");

    }


    @SneakyThrows
    @GetMapping("hello")
    public ResponseEntity hello() {

        List<String> result = new ArrayList<>();
        for (ProviderFactory u : SpiFactory.getProviderFactory(ProviderFactory.class)) {
            result.add(u.create().login());
        }
        return ResponseEntity.ok(result);
    }


}
