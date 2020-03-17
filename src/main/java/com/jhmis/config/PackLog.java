package com.jhmis.config;

import com.fundebug.Fundebug;
import com.jhmis.common.utils.MsgUtils;
import com.jhmis.core.web.BaseController;
import org.kohsuke.rngom.parse.host.Base;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.config
 * @Author: hdx
 * @CreateTime: 2020-01-10 14:54
 * @Description: 打包记录
 */
@Configuration
@Component
public class PackLog extends BaseController implements ApplicationRunner {
    @Value("${fundebug.apikey}")
    String apikey;

    @Value("${fundebug.version}")
    String version;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //进行发版记录
        logger.info("apikey="+ apikey +"version="+version);
        logger.error("apikey="+ apikey +"version="+version);
        Fundebug fundebug = new Fundebug(apikey);
        fundebug.notify("B2B发版履历", version + "已于" + new Date().toLocaleString()+"进行打包发版");
    }
}
