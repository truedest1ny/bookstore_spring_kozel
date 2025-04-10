package com.kozel.bookstore.data.connection;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public interface DataSource extends Closeable {
    Connection getConnection() throws SQLException;
}
