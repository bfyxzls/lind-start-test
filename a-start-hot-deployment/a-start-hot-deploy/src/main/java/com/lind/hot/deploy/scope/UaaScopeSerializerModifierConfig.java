package com.lind.hot.deploy.scope;

import com.lind.common.jackson.convert.AbstractJacksonSerializerModifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Slf4j
public class UaaScopeSerializerModifierConfig extends AbstractJacksonSerializerModifier {
    @Autowired
    UaaScopeSerializerModifier uaaScopeSerializerModifier;
    /**
     * 配置bean,依赖于ApplicationContext
     *
     * @return
     */
    @Bean
    @DependsOn("springContextUtils")
    public MappingJackson2HttpMessageConverter uaaScopeSerializerModifierConvert() {
        return super.callSelfSerializerModifier(uaaScopeSerializerModifier);
    }
}