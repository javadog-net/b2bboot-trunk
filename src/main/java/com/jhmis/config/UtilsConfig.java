package com.jhmis.config;

import com.jhmis.common.utils.MsgUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.config
 * @Author: hdx
 * @CreateTime: 2019-09-25 10:28
 * @Description: 工具配置类
 */
@Configuration
public class UtilsConfig {
    @Bean
    public MsgUtils MsgUtils(){
        return new MsgUtils();
    }
}
