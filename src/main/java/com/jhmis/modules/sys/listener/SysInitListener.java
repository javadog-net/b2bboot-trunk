package com.jhmis.modules.sys.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * <b>系统启动监听器</b>
 * 
 * @author tity
 * @date 2017-12-23
 */
public class SysInitListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//禁止ipv6
		System.setProperty("java.net.preferIPv4Stack","true");
		initSystemVars(sce.getServletContext());
	}
	
	/**
	 * 设置系统变量
	 * 
	 * @param servletContext
	 */
	private void initSystemVars(ServletContext servletContext){
		String cxtString = servletContext.getContextPath();
		System.setProperty("cxt", cxtString);
		String rootString = servletContext.getRealPath("/");
		System.setProperty("root", rootString);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
