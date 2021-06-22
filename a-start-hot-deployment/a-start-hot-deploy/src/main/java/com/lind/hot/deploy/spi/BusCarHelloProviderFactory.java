package com.lind.hot.deploy.spi;

/**
 * 公交车工厂.
 */
public class BusCarHelloProviderFactory implements CarHelloProviderFactory<BusCarHelloProvider> {


    @Override
    public BusCarHelloProvider create() {
        return new BusCarHelloProvider();
    }

    @Override
    public String getId() {
        return "BusCarHelloProvider";
    }
}
