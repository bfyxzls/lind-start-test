package com.lind.http2.controller;

import cn.hutool.core.io.FileUtil;
import com.lind.http2.config.HttpClientUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class Http2SSLController {
    /**
     * test http2.
     *
     * @param client
     * @param request
     * @return
     */
    private static String sendRequest(OkHttpClient client, Request request) {
        String result = null;
        String protocolName = null;
        try {
            Response response = client.newCall(request).execute();
            if (response != null) {
                protocolName = response.protocol().name();
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("测试app的http协议：{}", protocolName);
        return result + ": " + protocolName;
    }

    @GetMapping("/hello")
    public String hello() {

        String url = "https://localhost:8443/test";
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = HttpClientUtils.getTLSOKHttp();
        return sendRequest(client, request);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @SneakyThrows
    @GetMapping("/ok")
    public ResponseEntity ok() {
        String path = "D:\\test\\";
        List<String> names = FileUtil.listFileNames(path);
        for (String name : names) {
            String url = "https://localhost:8443/get-file?path=" + name;
            OkHttpClient okHttpClient = HttpClientUtils.getTLSOKHttp();
            final Request request = new Request.Builder()
                    .url(url)
                    .get()//默认就是GET请求，可以不写
                    .build();
            Call call = okHttpClient.newCall(request);


            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    log.error("onFailure:{}", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseBody body = response.body();
                    if (body != null) {
                        log.info("success body:{}", new String(body.bytes()));
                        body.close();
                    }
                }
            });

        }

        return ResponseEntity.ok("success");
    }
}
