package com.jhmis.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 17:06 2019/4/17
 * @Modified By
 */
@ConfigurationProperties(prefix = "useUrl")
public class UseUrl {
    public String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
