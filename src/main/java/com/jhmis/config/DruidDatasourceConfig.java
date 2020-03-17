package com.jhmis.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnClass(DruidDataSource.class)
public class DruidDatasourceConfig {
	@Value("${jdbc.url}")    
    private String jdbcUrl;    
    @Value("${jdbc.username}")    
    private String username;    
    @Value("${jdbc.password}")    
    private String password;    
    @Value("${jdbc.driver}")
    private String driverClassName;    
    @Value("${jdbc.pool.init}")    
    private int initialSize;    
    @Value("${jdbc.pool.minIdle}")    
    private int minIdle;    
    @Value("${jdbc.pool.maxActive}")    
    private int maxActive;     
    @Value("${jdbc.testSql}")    
    private String validationQuery;
    @Value("${jdbc.dual}")
    private String dual;
    @Value("${jdbc.druid.stat.enabled}")
    private boolean statEnabled; 
    @Value("${jdbc.druid.wall.enabled}")
    private boolean wallEnabled;
    
	@Bean(name="dataSource")
    @Primary //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource druidDataSource() {  
        DruidDataSource druidDataSource = new DruidDataSource(); 
//        数据源驱动类可不写，Druid默认会自动根据URL识别DriverClass
        druidDataSource.setDriverClassName(driverClassName); 
//        基本属性 url、user、password
        druidDataSource.setUrl(jdbcUrl);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
//        配置初始化大小、最小、最大
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
//        配置获取连接等待超时的时间
        druidDataSource.setMaxWait(60000L);
//		配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000L);
//		配置一个连接在池中最小生存的时间，单位是毫秒
        druidDataSource.setMinEvictableIdleTimeMillis(300000L);
		
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);

//		打开PSCache，并且指定每个连接上PSCache的大小（Oracle使用）
        if(driverClassName.contains("oracle")){
        	druidDataSource.setPoolPreparedStatements(true);
        	druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        }
        List<Filter> filters = new ArrayList<>();
        if(statEnabled){
        	filters.add(statFilter());
        }
        if(wallEnabled){
        	filters.add(wallFilter());
        }
        if(filters.size()>0){
        	druidDataSource.setProxyFilters(filters);
        }
        return druidDataSource;  
    }  
    
    private StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        statFilter.setSlowSqlMillis(1000);
        return statFilter;
    }

    private WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();
        // 允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        wallFilter.setConfig(config);
        return wallFilter;
    }
    
    @Bean
    @ConditionalOnBean(DataSource.class)
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        return servletRegistrationBean;
    }
    
	
}
