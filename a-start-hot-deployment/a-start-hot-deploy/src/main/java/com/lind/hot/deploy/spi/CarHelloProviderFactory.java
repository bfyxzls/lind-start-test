package com.lind.hot.deploy.spi;

import com.lind.spi.ProviderFactory;

/**
 * CarHello工厂，会有CarHelloProvider个性化业务.
 *
 * @param <T>
 */
public interface CarHelloProviderFactory<T extends CarHelloProvider> extends ProviderFactory<T> {
    /**
     * 重写父接口的create()方法，保存返回的T类型为CarHelloProvider.
     *
     * @return
     */
    @Override
    T create();
}
