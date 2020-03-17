package com.jhmis.api.Test;

import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.cache.LoggerCache;
import com.jhmis.common.json.AjaxJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.api.Test
 * @Author: hdx
 * @CreateTime: 2019-08-28 15:28
 * @Description: TestController
 */
@RestController
@RequestMapping("/test")
public class TestController {
    Logger log = LoggerFactory.getLogger(TestController.class);
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @GetMapping("/now")
    public String now(){
        // 从缓存中获取日志
        Logger LOG = LoggerCache.getLoggerByClassName(this.getClass().getName());
        LOG.info("自定义日志 ==》 日志内容。。。");
        return sdf.format(new Date());
    }

    @GetMapping("/hello")
    @SysLog(desc = "海尔b2b")
    public AjaxJson hello(String name){
        log.info("你妹");
        return AjaxJson.ok("haha");
    }

}
