package com.jhmis.config;

import com.haier.link.mq.model.SubscribeInfo;
import com.haier.link.mq.service.LinkMQSubscribeService;
import com.haier.link.upper.service.LinkProductUpperReadService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 10:34 2018/8/18
 * @Modified By
 */
public class RabbitMQConfig implements InitializingBean, ApplicationContextAware {


    @Lazy
    @Autowired
    private LinkMQSubscribeService linkMQSubscribeService;

    @Override
    public void afterPropertiesSet() throws Exception {
        SubscribeInfo subscribeInfo = linkMQSubscribeService.subscribe("1","2","3","4");
        System.out.print(subscribeInfo);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.print("*****************~~~~setApplicationContext~~~~~***********************");
    }
}
