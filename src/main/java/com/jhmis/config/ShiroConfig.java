package com.jhmis.config;

import com.jhmis.core.security.shiro.FilterChainDefinitionMap;
import com.jhmis.core.security.shiro.session.CacheSessionDAO;
import com.jhmis.core.security.shiro.session.SessionDAO;
import com.jhmis.core.security.shiro.session.SessionManager;
import com.jhmis.modules.sys.security.*;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.nutz.j2cache.shiro.J2CacheManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 权限拦截配置
 *
 */
@Configuration
//@ConditionalOnBean(Realm.class)
//@ConditionalOnClass(RememberMeManager.class)
public class ShiroConfig {
	@Value("${shiro.defaultPath}")
	private String defaultPath;
	@Value("${shiro.loginUrl}")
	private String loginUrl;
	@Value("${shiro.logoutUrl}")
	private String logoutUrl;
	@Value("${shiro.successUrl}")
	private String successUrl;
	@Value("${shiro.filterChainDefinitions}")
	private String filterChainDefinitions;
	@Value("${shiro.defaultFilterChainDefinitions}")
	private String defaultFilterChainDefinitions;
	@Value("${session.sessionTimeout}")
	private Long sessionTimeout;
	@Value("${session.sessionTimeoutClean}")
	private Long sessionValidationInterval;
	@Value("${session.sessionIdName}")
	private String sessionIdName;

	/**
	 * CAS认证过滤器
	 */
	@SuppressWarnings("deprecation")
	@Bean
	public CasFilter casFilter() {
		CasFilter filter = new CasFilter();
		filter.setFailureUrl(loginUrl);
		return filter;
	}
	
	/**
	 * 指定本系统SESSIONID
	 */
	@Bean
	public SimpleCookie sessionIdCookie() {
		return new SimpleCookie(sessionIdName);
	}

	/**
	 * 自定义shrioSessionId
	 */
	private static class ShiroSessionIdGenerator implements SessionIdGenerator{
		@Override
		public Serializable generateId(Session session) {
			return UUID.randomUUID().toString().replaceAll("-", "");
		}
	}

	/**
	 * 自定义Session存储容器
	 */
	@Bean
	public SessionDAO sessionDAO(CacheManager shiroCacheManager) {
		CacheSessionDAO dao = new CacheSessionDAO();
		dao.setActiveSessionsCacheName("activeSessionsCache");
		dao.setSessionIdGenerator(new ShiroSessionIdGenerator());
		dao.setCacheManager(shiroCacheManager);
        return dao;
	}
	
	/**
	 * 定义授权缓存管理器
	 */
	@Bean
	public CacheManager shiroCacheManager() {
		return new J2CacheManager();
	}

	/**
	 * 自定义Realm管理，管理多realm
	 * */
	@Bean(name="authenticator")
	public ModularRealmAuthenticator modularRealmAuthenticator(){
		//自己重写的ModularRealmAuthenticator
		CustomModularRealmAuthenticator modularRealmAuthenticator = new CustomModularRealmAuthenticator();
		modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		return modularRealmAuthenticator;
	}

	@Bean
	public DealerAuthorizingRealm dealerAuthorizingRealm() {
		return new DealerAuthorizingRealm();
	}

    @Bean
    public PurchaserAuthorizingRealm purchaserAuthorizingRealm() {
        return new PurchaserAuthorizingRealm();
    }

	@Bean
	public SystemAuthorizingRealm systemAuthorizingRealm() {
		return new SystemAuthorizingRealm();
	}

	/**
	 * 定义Shiro安全管理配置
	 * @param sessionManager
	 * @param shiroCacheManager
	 * @return
	 */
	@Bean
	public DefaultWebSecurityManager securityManager(SessionManager sessionManager,CacheManager shiroCacheManager) {
		DefaultWebSecurityManager bean = new DefaultWebSecurityManager();
		//设置realm.
		bean.setAuthenticator(modularRealmAuthenticator());
		List<Realm> realms = new ArrayList<>();
		//添加多个Realm
		realms.add(systemAuthorizingRealm());
		realms.add(dealerAuthorizingRealm());
        realms.add(purchaserAuthorizingRealm());
		bean.setRealms(realms);
		//bean.setRealm(systemAuthorizingRealm);
        //自定义subjectFactory,jwt不使用session
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO)bean.getSubjectDAO();
        DefaultSessionStorageEvaluator storageEvaluator = (DefaultSessionStorageEvaluator)subjectDAO.getSessionStorageEvaluator();
        CustomSubjectFactory subjectFactory = new CustomSubjectFactory(storageEvaluator);
        bean.setSubjectFactory(subjectFactory);
        //
		bean.setSessionManager(sessionManager);
		bean.setCacheManager(shiroCacheManager);
        return bean;
	}

	/**
	 * 自定义会话管理配置
	 * @param sessionDAO
	 * @param sessionIdCookie
	 * @param shiroCacheManager
	 * @return
	 */
	@Bean
	public SessionManager sessionManager(SessionDAO sessionDAO,Cookie sessionIdCookie,CacheManager shiroCacheManager) {
		SessionManager bean = new SessionManager();
		bean.setSessionDAO(sessionDAO);
		bean.setSessionIdCookie(sessionIdCookie);
		bean.setCacheManager(shiroCacheManager);
		bean.setGlobalSessionTimeout(sessionTimeout);
		bean.setSessionValidationInterval(sessionValidationInterval);
		bean.setSessionValidationSchedulerEnabled(true);
		bean.setSessionIdCookie(sessionIdCookie);
		bean.setSessionIdCookieEnabled(true);
        return bean;
	}

	/**
	 * Shiro认证过滤器
	*/

	@SuppressWarnings("deprecation")
	@Bean
	public ShiroFilterFactoryBean shiroFilter(WebSecurityManager securityManager,
			CasFilter casFilter, FormAuthenticationFilter formAuthenticationFilter) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		bean.setLoginUrl(loginUrl);
		bean.setSuccessUrl(successUrl);
		Map<String, Filter> filters = bean.getFilters();
		filters.put("cas", casFilter);
		filters.put("authc", formAuthenticationFilter);
		filters.put("jwt", new JWTFilter());
		FilterChainDefinitionMap chains = new FilterChainDefinitionMap();
		chains.setFilterChainDefinitions(filterChainDefinitions);
		chains.setDefaultFilterChainDefinitions(defaultFilterChainDefinitions);
		
		bean.setFilterChainDefinitionMap(chains.getObject());
		return bean;
	}


	/*
	*
	 * Shiro 生命周期处理器，实现初始化和销毁回调
	*/
	@Bean(name="lifecycleBeanPostProcessor")
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/*
	*
	 * Shiro 过滤器代理配置
	*/
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator bean = new DefaultAdvisorAutoProxyCreator();
		bean.setProxyTargetClass(true);
		return bean;
	}

	/*
	 * 启用Shrio授权注解拦截方式，AOP式方法级权限检查
	*/
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
		bean.setSecurityManager(securityManager);
		return bean;
	}

	/**
	 * 必须加到过滤器中，否者不好使
	 * @return
	 */
	@Bean
	public FilterRegistrationBean delegatingFilterProxy(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shiroFilter");
		filterRegistrationBean.setFilter(proxy);
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.setAsyncSupported(true);
		return filterRegistrationBean;
	}
}
