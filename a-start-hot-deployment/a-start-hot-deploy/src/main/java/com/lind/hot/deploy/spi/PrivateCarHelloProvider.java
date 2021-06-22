package com.lind.hot.deploy.spi;

/**
 * 公交车.
 */
public class PrivateCarHelloProvider implements CarHelloProvider {
    @Override
    public String login() {
        return "PrivateCarHelloProvider login...";
    }

    @Override
    public void start() {
        System.out.println("私家车启动...");
    }
}
