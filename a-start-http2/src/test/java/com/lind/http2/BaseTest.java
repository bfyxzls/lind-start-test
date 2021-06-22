package com.lind.http2;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Seeker1Application.class})
@AutoConfigureMockMvc
public abstract class BaseTest {
    /**
     * 循环执行线程.
     */
    protected void periodicCheck(Integer loop, PassableRunnable runnable) throws InterruptedException {
        int count = 0;
        while (true) {
            count++;
            runnable.run();
            if (runnable.isPassed() || count >= loop) {
                log.info("periodicCheck exit {}",count);
                return;
            }
            TimeUnit.MILLISECONDS.sleep(50);
        }
    }

    public static interface PassableRunnable extends Runnable {

        boolean isPassed();
    }
}
