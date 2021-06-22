package com.lind.hot;

import com.lind.spi.ProviderFactory;

public class EmailHelloProviderFactory implements ProviderFactory<EmailHelloProvider> {

    @Override
    public EmailHelloProvider create() {
        return new EmailHelloProvider();
    }

    @Override
    public String getId() {
        return "EmailHelloProvider";
    }

}
