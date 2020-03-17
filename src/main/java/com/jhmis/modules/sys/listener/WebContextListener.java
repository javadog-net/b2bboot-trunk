package com.jhmis.modules.sys.listener;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

import com.jhmis.modules.sys.service.SystemService;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		/*
		//tity 2017-12-23
		//这里可以配置系统属性,目前本系统的cxt属性由jsp属性输出
		String cxtString = servletContext.getContextPath();
		System.setProperty("cxt", cxtString);
		String rootString = servletContext.getRealPath("/");
		System.setProperty("ROOT", rootString);
		*/
		//System.setProperty("java.net.preferIPv4Stack", "true"); //Disable IPv6 in JVM
		
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}
}
