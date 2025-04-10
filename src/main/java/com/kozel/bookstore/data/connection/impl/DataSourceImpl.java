package com.kozel.bookstore.data.connection.impl;

import com.kozel.bookstore.data.connection.DataSource;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.sql.Connection;

@Slf4j
public class DataSourceImpl implements DataSource, Closeable {

    private ConnectionPool connectionPool;

    public DataSourceImpl(String driver, String url, String user, String password, int poolSize) {

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
