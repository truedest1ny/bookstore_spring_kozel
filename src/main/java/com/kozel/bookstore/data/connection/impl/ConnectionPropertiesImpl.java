package com.kozel.bookstore.data.connection.impl;

import com.kozel.bookstore.data.connection.ConnectionProperties;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RequiredArgsConstructor
public class ConnectionPropertiesImpl implements ConnectionProperties {

    private final Properties PROPERTIES = new Properties();
    private final String FILE_NAME;
    

    public String getProperty(String property) {

        try (InputStream inputStream = ConnectionPropertiesImpl.class.getResourceAsStream(FILE_NAME)) {
            PROPERTIES.load(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return PROPERTIES.getProperty(property);
    }


}

