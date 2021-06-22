package com.lind.hot.deploy.spi;

/**
 * 公交车.
 */
public class BusCarHelloProvider implements CarHelloProvider {
    @Override
    public String login() {
        return "BusCarHelloProvider login...";
    }

    @Override
    public void start() {
        System.out.println("公交车启动...");
    }
}
