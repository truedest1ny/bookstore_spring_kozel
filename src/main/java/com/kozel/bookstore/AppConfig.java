package com.kozel.bookstore;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
@ComponentScan
public class AppConfig {

    @Value("${DRIVER_REMOTE}")
    private String driver;

    @Value("${URI_REMOTE}")
    private String url;

    @Value("${USER_REMOTE}")
    private String user;

    @Value("${PASSWORD_REMOTE}")
    private String password;

    @Value("${CONNECTION_POOL_SIZE}")
    private int poolSize;

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(poolSize);

        return dataSource;
    }
}