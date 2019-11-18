package com.yjy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;



@Configuration
public class DataSourceConfig {
	
	   
    @Bean(name = "user-our")
    @ConfigurationProperties(prefix = "spring.user-our") 
    public DruidDataSource dataSource1() {
    	return DruidDataSourceBuilder.create().build();
    }
    @Bean(name = "user-other")
    @ConfigurationProperties(prefix = "spring.user-other") 
    public DruidDataSource dataSource2() {
    	return DruidDataSourceBuilder.create().build();
    }

   

}
