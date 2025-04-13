package com.kozel.bookstore;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.impl.ErrorCommand;
import com.kozel.bookstore.controller.impl.book.*;
import com.kozel.bookstore.controller.impl.user.*;
import com.kozel.bookstore.data.connection.DataSource;
import com.kozel.bookstore.data.connection.impl.DataSourceImpl;
import com.kozel.bookstore.data.dao.BookDao;
import com.kozel.bookstore.data.dao.UserDao;
import com.kozel.bookstore.data.dao.impl.BookDaoImpl;
import com.kozel.bookstore.data.dao.impl.UserDaoImpl;
import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.impl.BookServiceImpl;
import com.kozel.bookstore.service.impl.UserServiceImpl;
import com.kozel.bookstore.service.mapper.DataMapper;
import com.kozel.bookstore.service.mapper.DataMapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application.properties")
public class AppConfig {

    @Bean
    public DataSource dataSource(@Value("${DRIVER_REMOTE}") String driver,
                                 @Value("${URI_REMOTE}") String uri,
                                 @Value("${USER_REMOTE}") String user,
                                 @Value("${PASSWORD_REMOTE}") String password,
                                 @Value("${CONNECTION_POOL_SIZE}") int poolSize){

        return new DataSourceImpl(driver, uri, user, password, poolSize);
    }

    @Bean
    public BookDao bookDao(DataSource dataSource){
        return new BookDaoImpl(dataSource);
    }

    @Bean
    public UserDao userDao(DataSource dataSource){
        return new UserDaoImpl(dataSource);
    }

    @Bean
    public DataMapper dataMapper(){
        return new DataMapperImpl();
    }

    @Bean
    public BookService bookService(BookDao bookDao, DataMapper dataMapper){
        return new BookServiceImpl(bookDao, dataMapper);
    }

    @Bean
    public UserService userService(UserDao userDao, DataMapper dataMapper){
        return new UserServiceImpl(userDao, dataMapper);
    }

    @Bean
    public Command book(BookService bookService){
        return new BookCommand(bookService);
    }

    @Bean
    public Command books(BookService bookService){
        return new BooksCommand(bookService);
    }

    @Bean
    public Command user(UserService userService){
        return new UserCommand(userService);
    }

    @Bean
    public Command users(UserService userService){
        return new UsersCommand(userService);
    }

    @Bean
    public Command error(){
        return new ErrorCommand();
    }

    @Bean
    public Command updateBook(BookService bookService){
        return new UpdateBookCommand(bookService);
    }

    @Bean
    public Command editBook(BookService bookService){
        return new EditBookCommand(bookService);
    }

    @Bean
    public Command updateUser(UserService userService){
        return new UpdateUserCommand(userService);
    }

    @Bean
    public Command editUser(UserService userService){
        return new EditUserCommand(userService);
    }

    @Bean
    public Command deleteBook(BookService bookService){
        return new BookDeleteCommand(bookService);
    }

    @Bean
    public Command deleteUser(UserService userService){
        return new UserDeleteCommand(userService);
    }

    @Bean
    public Command creatingBook(){
        return new BookCreatingCommand();
    }

    @Bean
    public Command createBook(BookService bookService){
        return new BookCreateCommand(bookService);
    }

    @Bean
    public Command creatingUser(){
        return new UserCreatingCommand();
    }

    @Bean
    public Command createUser(UserService userService){
        return new UserCreateCommand(userService);
    }

}
