package com.jhmis.config;

import com.haier.webservices.client.shortmsg.SendMsgApi;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.config
 * @Author: hdx
 * @CreateTime: 2019-09-23 15:59
 * @Description: 短信配置类
 */
@Configuration
public class ShortMsgConfig {
    @Bean
    public SendMsgApi SendMsgApi() {
        return new SendMsgApi();
    }
}
