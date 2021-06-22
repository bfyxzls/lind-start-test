package com.lind.hot.deploy.scope;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启Uaa的Scope授权功能.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({UaaScopeSerializerModifier.class, UaaScopeSerializerModifierConfig.class})
public @interface EnableScoping {
}
