package com.jhmis.config;

import javax.xml.ws.Endpoint;

import com.haier.webservices.server.service400.ShopMsg400ServiceImpl;
import com.haier.webservices.server.service690.ShopMsg690Service;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.haier.webservices.server.acg.AcgService;
import com.haier.webservices.server.acg.AcgServiceImpl;
import com.haier.webservices.server.service400.ShopMsg400Service;
import com.haier.webservices.server.hps.HpsService;
import com.haier.webservices.server.hps.HpsServiceImpl;
import com.haier.webservices.server.service690.ShopMsg690ServiceImpl;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.config
 * @Author: hdx
 * @CreateTime: 2019-08-14 15:55
 * @Description: cxf配置类
 */
@Configuration
public class CxfConfig {

    @Bean
    public ServletRegistrationBean dispatcherServletCXF() {
        //此处名字一定要改成别的，要不回盖掉原始默认的dispatcherServlet
    	 return new ServletRegistrationBean(new CXFServlet(),"/soap/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    @Bean
    public AcgService acgService() {
        return new AcgServiceImpl();
    }

    @Bean
    public HpsService hpsService() {
    	return new HpsServiceImpl();
    }
    @Bean
    public ShopMsg400Service shopMsg400Service() {
        return new ShopMsg400ServiceImpl();
    }
    @Bean
    public ShopMsg690Service shopMsg690Service() {
        return new ShopMsg690ServiceImpl();
    }
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), acgService());
        endpoint.publish("/acg");
        return endpoint;
    }
    @Bean
    public Endpoint hps_endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), hpsService());
        endpoint.publish("/hps");
        return endpoint;
    }
    @Bean
    public Endpoint forzerozero_endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), shopMsg400Service());
        endpoint.publish("/service400");
        return endpoint;
    } 
    @Bean
    public Endpoint sixninezero_endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), shopMsg690Service());
        endpoint.publish("/service690");
        return endpoint;
    }
}

