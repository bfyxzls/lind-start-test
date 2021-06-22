package com.lind.hot.deploy.spi;

/**
 * 私家车工厂.
 */
public class PrivateCarHelloProviderFactory implements CarHelloProviderFactory<PrivateCarHelloProvider> {


    @Override
    public PrivateCarHelloProvider create() {
        return new PrivateCarHelloProvider();
    }

    @Override
    public String getId() {
        return "PrivateCarHelloProvider";
    }
}
