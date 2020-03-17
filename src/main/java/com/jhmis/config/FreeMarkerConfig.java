package com.jhmis.config;

import com.jhmis.modules.cms.directive.LinkListDirective;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * freemarker 配置类
 */
//@Configuration
public class FreeMarkerConfig {
    @Autowired
    private LinkListDirective linkDirective;
    @Autowired
    private freemarker.template.Configuration  configuration;

    @PostConstruct //当DI容器实例化当前受管Bean时，@PostConstruct注解的方法会被自动触发，从而完成一些初始化工作
    public void setSharedVariable() {
        configuration.setSharedVariable("link",linkDirective);
    }

}
