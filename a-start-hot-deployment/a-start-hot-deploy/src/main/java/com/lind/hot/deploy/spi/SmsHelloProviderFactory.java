package com.lind.hot.deploy.spi;

import com.lind.spi.ProviderFactory;

public class SmsHelloProviderFactory implements ProviderFactory<SmsHelloProvider> {

    @Override
    public SmsHelloProvider create() {
        return new SmsHelloProvider();
    }

    @Override
    public String getId() {
        return "SmsHelloProvider";
    }

}
