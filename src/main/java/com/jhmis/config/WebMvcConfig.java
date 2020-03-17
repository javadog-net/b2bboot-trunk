package com.jhmis.config;

import com.jhmis.common.config.Global;
import com.jhmis.core.mapper.JsonMapper;
import com.jhmis.core.servlet.FhtmlServlet;
import com.jhmis.core.servlet.UserfilesDownloadServlet;
import com.jhmis.core.servlet.ValidateCodeServlet;
import com.jhmis.modules.sys.interceptor.LogInterceptor;
import com.jhmis.modules.sys.listener.SysInitListener;
import com.opensymphony.sitemesh.webapp.SiteMeshFilter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter implements EmbeddedServletContainerCustomizer {
    /**
     * 自定义错误处理页，对应web.xml中的ErrorPage
     * @param configurableEmbeddedServletContainer
     */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/webpage/error/404.jsp"));
        configurableEmbeddedServletContainer.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/webpage/error/500.jsp"));
    }

    @Bean
    public FilterRegistrationBean encodingFilterRegistration() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        FilterRegistrationBean registration = new FilterRegistrationBean(
                encodingFilter);
        registration.setName("encodingFilter");
        registration.addUrlPatterns("/*");
        registration.setAsyncSupported(true);
        registration.setOrder(1);
        return registration;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // String解析器
        converters.add(stringHttpMessageConverter());
        // json解析器
        converters.add(mappingJackson2HttpMessageConverter());
        // xml解析器
        converters.add(marshallingHttpMessageConverter());
    }

    private StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    /**
     * json解析器
     *
     * @return
     */
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(MediaType.TEXT_HTML);
        converter.setSupportedMediaTypes(mediaTypes);
        converter.setPrettyPrint(false);
        converter.setObjectMapper(new JsonMapper());
        return converter;
    }

    /**
     * xml解析器
     *
     * @return
     */
    private MarshallingHttpMessageConverter marshallingHttpMessageConverter() {
        MarshallingHttpMessageConverter converter = new MarshallingHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.TEXT_XML);
        mediaTypes.add(MediaType.APPLICATION_XML);
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setStreamDriver(new StaxDriver());
        xStreamMarshaller.setAnnotatedClasses(com.jhmis.core.persistence.BaseEntity.class);
        converter.setSupportedMediaTypes(mediaTypes);
        converter.setMarshaller(xStreamMarshaller);
        converter.setUnmarshaller(xStreamMarshaller);
        return converter;
    }

    /**
     * REST中根据URL后缀自动判定Content-Type及相应的View
     *
     * @return
     */
    @Bean
    public ContentNegotiationManagerFactoryBean contentNegotiationManager() {
        ContentNegotiationManagerFactoryBean bean = new ContentNegotiationManagerFactoryBean();
        Properties mediaTypes = new Properties();
        mediaTypes.put("xml", "application/xml");
        mediaTypes.put("json", "application/json");
        bean.setMediaTypes(mediaTypes);
        bean.setIgnoreAcceptHeader(true);
        bean.setFavorPathExtension(true);
        return bean;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
        config.setTemplateLoaderPath("/webpage/");
        return config;
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setSuffix(".html");
        viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setOrder(0);
        return viewResolver;
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/webpage/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setOrder(1);
        return viewResolver;
    }

    // 配置文件上传,commonsMultipartResolver与spring冲突
    /*@Bean(name = {"multipartResolver"})
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(10485760000L);
        commonsMultipartResolver.setMaxInMemorySize(40960);
        return commonsMultipartResolver;
    }*/


    /**
     * 文件上传配置,直接在application.yaml中配置，可以不需要配置bean
     * @return
     */
    /*@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("10MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }*/

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/index.jsp");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    // 资源重定向(仅作为后台使用不提供静态资源)
    //springboot 的默认static目录为根目录，配置了static后会导致resouce中的static失效，只能使用webapp中的static,暂时先使用webapp中的
    //TODO 待测试
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(31536000);
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        String staticPath = Global.getConfig("cms.static.path");
        registry.addResourceHandler("/**").addResourceLocations("file:"+staticPath+"/");
    }

    //初始化监听器
    @Bean
    public SysInitListener sysInitListener() {
        SysInitListener listen = new SysInitListener();
        return listen;
    }

    //请求监听器
    @Bean
    public RequestContextListener requestContextListener() {
        RequestContextListener listen = new RequestContextListener();
        return listen;
    }


    //上下文监听器
   /* @Bean
    public WebContextListener webContextListener() {
    	WebContextListener listen = new WebContextListener();
        return listen;
    }*/
    @Bean
    public FilterRegistrationBean sitemeshFilterRegistration() {
        SiteMeshFilter sitemeshFilter = new SiteMeshFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean(
                sitemeshFilter);
        registration.setName("sitemeshFilter");
        registration.addUrlPatterns("/*");
        registration.setAsyncSupported(true);
        return registration;
    }

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //日志拦截器
        LogInterceptor logInterceptor = new LogInterceptor();
        String adminPath = Global.getAdminPath();
        registry.addInterceptor(logInterceptor).addPathPatterns(adminPath+"/**").excludePathPatterns(adminPath+"/", adminPath+"/login",
                adminPath+"/sys/menu/treeData", adminPath+"/oa/oaNotify/self/count");
        super.addInterceptors(registry);
    }

    /**
     * 文件访问
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean UserfilesDownloadServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean();
        bean.setServlet(new UserfilesDownloadServlet());
        bean.addUrlMappings("/userfiles/*");
        return bean;
    }

    /**
     * 图形验证码
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean ValidateCodeServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean();
        bean.setServlet(new ValidateCodeServlet());
        bean.addUrlMappings("/servlet/validateCodeServlet");
        return bean;
    }

    /**
     * 处理fhtml的动态解析
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean FhtmlServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean();
        bean.setServlet(new FhtmlServlet());
        bean.addUrlMappings("*.fhtml");
        return bean;
    }
}
