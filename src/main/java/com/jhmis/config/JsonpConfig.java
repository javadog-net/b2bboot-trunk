package com.jhmis.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Spring Boot支持跨域请求的JSONP数据.
 */
@ControllerAdvice(basePackages = {"com.jhmis.api","com.jhmis.core.web"})
public class JsonpConfig  extends AbstractJsonpResponseBodyAdvice {

    public JsonpConfig() {
        super("callback", "jsonp");
    }
}
