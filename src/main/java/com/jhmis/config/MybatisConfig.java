package com.jhmis.config;

import com.jhmis.common.config.Global;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
@org.springframework.context.annotation.Configuration
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
@ConditionalOnBean(DataSource.class)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties(MybatisProperties.class)
//@MapperScan(basePackages = "com.jhmis", sqlSessionFactoryRef = "sqlSessionFactory",annotationClass = MyBatisMapper.class)

public class MybatisConfig {
  @Autowired
  private MybatisProperties properties;
  @Autowired
  private ResourceLoader resourceLoader = new DefaultResourceLoader();


  @PostConstruct
  public void checkConfigFileExists() {
    if (this.properties.isCheckConfigLocation() && StringUtils.hasText(this.properties.getConfigLocation())) {
      Resource resource = this.resourceLoader.getResource(this.properties.getConfigLocation());
      Assert.state(resource.exists(), "Cannot find config location: " + resource
          + " (please add config file or check your Mybatis configuration)");
    }
  }

  @Bean
  @ConditionalOnMissingBean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
    factory.setDataSource(dataSource);
    factory.setVfs(SpringBootVFS.class);
    if (StringUtils.hasText(this.properties.getConfigLocation())) {
      factory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
    }
    Configuration configuration = this.properties.getConfiguration();
    if (configuration == null && !StringUtils.hasText(this.properties.getConfigLocation())) {
      configuration = new Configuration();
    }
    factory.setConfiguration(configuration);
    if (this.properties.getConfigurationProperties() != null) {
      factory.setConfigurationProperties(this.properties.getConfigurationProperties());
    }
    if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
      factory.setTypeAliasesPackage(this.properties.resolveTypeAliasesPackage());
    }
    if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
      factory.setMapperLocations(this.properties.resolveMapperLocations());
    }

    return factory.getObject();
  }
	
  @Bean
  @ConditionalOnMissingBean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    ExecutorType executorType = this.properties.getExecutorType();
    if (executorType != null) {
      return new SqlSessionTemplate(sqlSessionFactory, executorType);
    } else {
      return new SqlSessionTemplate(sqlSessionFactory);
    }
  }

    @Bean
    @ConditionalOnBean(SqlSessionFactory.class)
    @ConditionalOnProperty(prefix = "mybatis", name = "enableRefresh", havingValue = "true", matchIfMissing = false)
    public MybatisMapperDynamicLoader mybatisMapperDynamicLoader() {
        MybatisMapperDynamicLoader loader = new MybatisMapperDynamicLoader();
        return loader;
    }

  @Bean
  public static MapperScannerConfigurer configurer() {
      MapperScannerConfigurer configurer = new MapperScannerConfigurer();
      configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
      configurer.setAnnotationClass(MyBatisMapper.class);
      String basePackage = Global.getConfig("mybatis.scanBasePackage","com.jhmis");
      configurer.setBasePackage(basePackage);
      return configurer;
  } 
}
