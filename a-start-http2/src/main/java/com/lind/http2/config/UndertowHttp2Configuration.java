package com.lind.http2.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.undertow.UndertowOptions.ENABLE_HTTP2;

/**
 * Undertow http2 h2c 配置
 *
 * @author L.cm
 */
@Configuration
@ConditionalOnClass(ServerProperties.Undertow.class)
@AutoConfigureBefore(ServletWebServerFactoryAutoConfiguration.class)
public class UndertowHttp2Configuration {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        // 开启 undertow http2
        factory.addBuilderCustomizers(builder -> builder.setServerOption(ENABLE_HTTP2, true));
        return factory;
    }

}
