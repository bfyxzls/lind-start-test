package com.lind.hot.deploy.spi;

import com.lind.spi.Provider;

public class SmsHelloProvider implements Provider {


    @Override
    public String login() {
        return "SmsHelloProvider";
    }
}
