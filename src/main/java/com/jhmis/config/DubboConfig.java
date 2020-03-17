package com.jhmis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConditionalOnProperty(prefix = "dubbo", name = "enable", havingValue = "true", matchIfMissing = false)
@PropertySource("classpath:dubbo/dubbo.properties")
@ImportResource({ "classpath:dubbo/dubbo-consume.xml" })
public class DubboConfig {

}
