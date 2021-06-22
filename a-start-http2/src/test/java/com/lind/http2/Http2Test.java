package com.lind.http2;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
public class Http2Test extends BaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void apiTest() {
        try {
            log.info("result...");
            periodicCheck(5, new PassableRunnable() {
                @Override
                public boolean isPassed() {
                    return false;
                }

                @SneakyThrows
                @Override
                public void run() {
                    mockMvc.perform(MockMvcRequestBuilders
                            .get("/ok"))
                            .andExpect(MockMvcResultMatchers.status().isOk())
                            .andDo(MockMvcResultHandlers.print());
                }
            });

            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
