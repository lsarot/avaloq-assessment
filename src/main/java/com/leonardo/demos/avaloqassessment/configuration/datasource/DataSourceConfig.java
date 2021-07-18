package com.leonardo.demos.avaloqassessment.configuration.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
@Configuration
public class DataSourceConfig {


    @Primary
    @Bean("MyDataSource")
    public DataSource configureH2DataSource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:mem:avaloqsim;DB_CLOSE_DELAY=-1;INIT=runscript from 'classpath:/db_schema.sql'");
        dataSource.setUsername("user");
        dataSource.setPassword("pass");
        return dataSource;
    }

}
