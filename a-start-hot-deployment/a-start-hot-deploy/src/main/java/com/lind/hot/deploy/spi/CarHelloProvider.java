package com.lind.hot.deploy.spi;

import com.lind.spi.Provider;

/**
 * CarHello业务实现者，会有个性化方法.
 */
public interface CarHelloProvider extends Provider {
    void start();
}
