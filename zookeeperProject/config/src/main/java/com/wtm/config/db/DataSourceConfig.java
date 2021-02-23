package com.wtm.config.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(){
        EnjoyDataSource enjoyDataSource = new EnjoyDataSource();
        enjoyDataSource.setUrl("");
        enjoyDataSource.setUsername("");
        enjoyDataSource.setPassword("");
        enjoyDataSource.setDefaultReadOnly(false);
        return enjoyDataSource;
    }
}
