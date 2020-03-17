package com.jhmis.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;

import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

    @Bean(name = "primaryDataSource")

    @Qualifier("primaryDataSource")//该注解指定注入的Bean的名称，Spring框架使用byName方式寻找合格的 bean，这样就消除了byType方式产生的歧义

    @ConfigurationProperties(prefix="spring.datasource.primary")//读取配置文件里前缀 为"spring.datasource.primary"的语句

    public DataSource primaryDataSource() {

        return DataSourceBuilder.create().build();

    }

    @Bean(name = "primaryJdbcTemplate")

    public JdbcTemplate primaryJdbcTemplate(

            @Qualifier("primaryDataSource") DataSource dataSource) {

        return new JdbcTemplate(dataSource);

    }


    @Bean(name = "secondDataSource")

    @Qualifier("secondDataSource")//该注解指定注入的Bean的名称，Spring框架使用byName方式寻找合格的 bean，这样就消除了byType方式产生的歧义

    @ConfigurationProperties(prefix="spring.datasource.second")//读取配置文件里前缀 为"spring.datasource.second"的语句

    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();

    }

    @Bean(name = "secondJdbcTemplate")

    public JdbcTemplate secondJdbcTemplate(

            @Qualifier("secondDataSource") DataSource dataSource) {

        return new JdbcTemplate(dataSource);

    }

}