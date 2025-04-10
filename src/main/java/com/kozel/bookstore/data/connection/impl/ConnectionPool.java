package com.kozel.bookstore.data.connection.impl;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


@Slf4j
class ConnectionPool {
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final int poolSize;

    ConnectionPool (String driver, String url, String user, String password, int poolSize){
        this.poolSize = poolSize;
        this.freeConnections = new LinkedBlockingDeque<>();


        try {

            Class.forName(driver);
            log.info("Database driver loaded");


            for (int i = 0; i < this.poolSize; i++)
            {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnections.add(new ProxyConnection(connection, this));
                log.info("Connection created");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            log.error(ex.getMessage(), ex);
        }


    }

    public Connection getConnection(){
        ProxyConnection connection = null;

        try {
            connection = freeConnections.take();
        } catch (InterruptedException ex){
            log.error(ex.getMessage(), ex);
        }

        return connection;
    }

    public void releaseConnection(Connection connection){
        if (connection instanceof ProxyConnection proxyConnection){
            freeConnections.add(proxyConnection);
        } else {
            log.warn("Returned not a proxy connection");
        }
    }

    public void destroyPool () {
        for (int i = 0; i < poolSize; i++) {
            try {
                ProxyConnection connection = freeConnections.take();
                connection.reallyClose();
                log.info("Connection closed");

            } catch (SQLException | InterruptedException ex) {
                log.error(ex.getMessage(), ex);
            }

        }
    }

}
