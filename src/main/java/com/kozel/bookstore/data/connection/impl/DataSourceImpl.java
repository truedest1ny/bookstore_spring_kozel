package com.kozel.bookstore.data.connection.impl;

import com.kozel.bookstore.data.connection.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.Closeable;
import java.sql.Connection;

@Repository
@Slf4j
public class DataSourceImpl implements DataSource, Closeable {

    private ConnectionPool connectionPool;

    public DataSourceImpl(@Value("${DRIVER_REMOTE}") String driver,
                          @Value("${URI_REMOTE}") String url,
                          @Value("${USER_REMOTE}") String user,
                          @Value("${PASSWORD_REMOTE}") String password,
                          @Value("${CONNECTION_POOL_SIZE}") int poolSize) {

        connectionPool = new ConnectionPool(driver, url, user, password, poolSize);
        log.info("Connection pool created");
    }

    @Override
    public Connection getConnection(){
        return connectionPool.getConnection();
    }

    @Override
    public void close(){
        if (connectionPool != null){
            connectionPool.destroyPool();
            connectionPool = null;
            log.info("Connection pool destroyed");
        }
    }
}
