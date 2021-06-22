package com.lind.hot;

import com.lind.spi.Provider;

public class EmailHelloProvider implements Provider {


    @Override
    public String login() {
        return "EmailHelloProvider";
    }
}
