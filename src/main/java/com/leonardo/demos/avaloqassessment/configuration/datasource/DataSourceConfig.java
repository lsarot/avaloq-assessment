package com.leonardo.demos.avaloqassessment.configuration.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Autowired private Environment env;


    @Bean("MyDataSource")
    public DataSource configureH2DataSource_2() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:mem:avaloqsim;DB_CLOSE_DELAY=-1;INIT=runscript from 'classpath:/db_schema.sql'");
        dataSource.setUsername("user");
        dataSource.setPassword("pass");
        return dataSource;
    }

}
