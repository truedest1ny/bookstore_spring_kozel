package com.kozel.bookstore.controller;

import com.kozel.bookstore.controller.impl.ErrorCommand;
import com.kozel.bookstore.controller.impl.book.*;
import com.kozel.bookstore.controller.impl.user.*;
import com.kozel.bookstore.data.connection.ConnectionProperties;
import com.kozel.bookstore.data.connection.DataSource;
import com.kozel.bookstore.data.connection.impl.ConnectionPropertiesImpl;
import com.kozel.bookstore.data.connection.impl.DataSourceImpl;
import com.kozel.bookstore.data.dao.BookDao;
import com.kozel.bookstore.data.dao.UserDao;
import com.kozel.bookstore.data.dao.impl.BookDaoImpl;
import com.kozel.bookstore.data.dao.impl.UserDaoImpl;
import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.exception.IllegalCommandException;
import com.kozel.bookstore.service.impl.BookServiceImpl;
import com.kozel.bookstore.service.impl.UserServiceImpl;
import com.kozel.bookstore.service.mapper.DataMapper;
import com.kozel.bookstore.service.mapper.DataMapperImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class CommandFactory implements Closeable {

    public static final CommandFactory INSTANCE = new CommandFactory();
    private final HashMap<String, Command> commandHashMap;
    private final List<Closeable> closeables;


    private CommandFactory() {

        commandHashMap = new HashMap<>();
        closeables = new ArrayList<>();


        ConnectionProperties connectionProperties = new ConnectionPropertiesImpl("/application.properties");

        String driver = connectionProperties.getProperty("DRIVER_REMOTE");
        String uri = connectionProperties.getProperty("URI_REMOTE");
        String user = connectionProperties.getProperty("USER_REMOTE");
        String password = connectionProperties.getProperty("PASSWORD_REMOTE");
        int poolSize = Integer.parseInt(connectionProperties.getProperty("CONNECTION_POOL_SIZE"));

        DataSource dataSource = new DataSourceImpl(driver, uri, user, password, poolSize);

        closeables.add(dataSource);

        BookDao bookDao = new BookDaoImpl(dataSource);
        UserDao userDao = new UserDaoImpl(dataSource);
        DataMapper dataMapper = new DataMapperImpl();
        BookService bookService = new BookServiceImpl(bookDao, dataMapper);
        UserService userService = new UserServiceImpl(userDao, dataMapper);


        commandHashMap.put("book", new BookCommand(bookService));
        commandHashMap.put("books", new BooksCommand(bookService));
        commandHashMap.put("user", new UserCommand(userService));
        commandHashMap.put("users", new UsersCommand(userService));
        commandHashMap.put("error", new ErrorCommand());
        commandHashMap.put("updateBook", new UpdateBookCommand(bookService));
        commandHashMap.put("editBook", new EditBookCommand(bookService));
        commandHashMap.put("updateUser", new UpdateUserCommand(userService));
        commandHashMap.put("editUser", new EditUserCommand(userService));
        commandHashMap.put("deleteBook", new BookDeleteCommand(bookService));
        commandHashMap.put("deleteUser", new UserDeleteCommand(userService));
        commandHashMap.put("creatingBook", new BookCreatingCommand());
        commandHashMap.put("createBook", new BookCreateCommand(bookService));
        commandHashMap.put("creatingUser", new UserCreatingCommand());
        commandHashMap.put("createUser", new UserCreateCommand(userService));

    }

    public Command get(String commandParam){
       Command command = commandHashMap.get(commandParam);
        if (command == null) {
            throw new IllegalCommandException("Not found requested command " + "(" + commandParam + ")");
        }
        return command;
    }

    @Override
    public void close(){
        try {
            for (Closeable closeable : closeables){
                closeable.close();
            }
        } catch (IOException ex){
            log.error(ex.getMessage());
        }

    }
}

