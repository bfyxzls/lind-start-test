package com.lind.hot.deploy.scope;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScopeSet {
    /**
     * 授权范围.
     *
     * @return
     */
    String value() default "";
}
