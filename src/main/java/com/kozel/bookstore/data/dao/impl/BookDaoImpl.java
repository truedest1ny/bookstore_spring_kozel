package com.kozel.bookstore.data.dao.impl;

import com.kozel.bookstore.data.connection.DataSource;
import com.kozel.bookstore.data.dao.BookDao;
import com.kozel.bookstore.data.entity.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {


    private static final String ADD_BOOK_SQL =
            "INSERT INTO books (name, isbn, cover_id, author, published_year, price) " +
                        "VALUES (?, ?, (SELECT id FROM covers WHERE enum_value = ?), ?, ?, ?)";
    private static final String GET_ID_ADD_SQL =
            "SELECT id FROM books WHERE isbn = ?";

    private static final String GET_ALL_SQL =
            "SELECT bk.id, bk.name, bk.isbn, cv.enum_value, bk.author, bk.published_year, bk.price FROM books bk" +
                    " JOIN covers cv ON bk.cover_id = cv.id ORDER BY bk.id";

    private static final String GET_BY_ID_SQL =
            "SELECT bk.id, bk.name, bk.isbn, cv.enum_value, bk.author, bk.published_year, bk.price FROM books bk" +
                    " JOIN covers cv ON bk.cover_id = cv.id WHERE bk.id = ?";

    private static final String GET_BY_ISBN_SQL =
            "SELECT bk.id, bk.name, bk.isbn, cv.enum_value, bk.author, bk.published_year, bk.price FROM books bk" +
                    " JOIN covers cv ON bk.cover_id = cv.id WHERE bk.isbn = ?";
    private static final String GET_BY_AUTHOR_SQL =
            "SELECT bk.id, bk.name, bk.isbn, cv.enum_value, bk.author, bk.published_year, bk.price FROM books bk" +
                    " JOIN covers cv ON bk.cover_id = cv.id WHERE bk.author = ?";

    private static final String UPDATE_SQL =
            "UPDATE books SET " +
                    "name = ?, " +
                    "isbn = ?, " +
                    "cover_id = (SELECT id FROM covers WHERE enum_value = ?), " +
                    "author = ?, " +
                    "published_year = ?, " +
                    "price = ? " +
                    "WHERE id = ?";
    private static final String DELETE_BY_ID_SQL =
            "DELETE FROM books WHERE id = ?";

    private static final String COUNT_ALL_SQL =
            "SELECT COUNT (id) FROM books";


    private final DataSource dataSource;


    @Override
    public Long save(Book book) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_BOOK_SQL);

                statement.setString(1, book.getName());
                statement.setString(2, book.getIsbn());
                statement.setString(3, book.getCover().toString());
                statement.setString(4, book.getAuthor());
                statement.setInt(5, book.getPublishedYear());
                statement.setBigDecimal(6, book.getPrice());
                statement.executeUpdate();

            statement = connection.prepareStatement(GET_ID_ADD_SQL);


                statement.setString(1, book.getIsbn());
                ResultSet resultSet = statement.executeQuery();

                log.debug("Query to database completed");

                if (resultSet.next())
                {
                    book.setId(resultSet.getLong("id"));
                    return book.getId();
                }
                return null;
            }

         catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Book> findAll() {

        List<Book> books = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery(GET_ALL_SQL);

            log.debug("Query to database completed");

            while (resultSet.next())
            {
                Book book = process(resultSet);
                books.add(book);
            }


        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }

        return books;
    }


    @Override
    public Book findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID_SQL);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            log.debug("Query to database completed");

            if (resultSet.next())
            {
                return process(resultSet);
            }
            return null;
        } catch (SQLException exception){
            throw new RuntimeException(exception);
         }
    }

    @Override
    public Book findByIsbn(String isbn) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_ISBN_SQL);
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();

            log.debug("Query to database completed");

            if (resultSet.next())
            {
                return process(resultSet);
            }
            return null;
        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }

    }

    @Override
    public List<Book> findByAuthor(String author) {

        List<Book> books = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_AUTHOR_SQL);
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();

            log.debug("Query to database completed");

            while (resultSet.next())
            {
                Book book = process(resultSet);
                books.add(book);
            }

        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
        return books;
    }

    @Override
    public Book update(Book book) {

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);

            statement.setString(1, book.getName());
            statement.setString(2, book.getIsbn());
            statement.setString(3, book.getCover().toString());
            statement.setString(4, book.getAuthor());
            statement.setInt(5, book.getPublishedYear());
            statement.setBigDecimal(6, book.getPrice());
            statement.setLong(7, book.getId());

            log.debug("Query to database completed");

        if (statement.executeUpdate() > 0)
        {
            return book;
        }
            return null;

        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean deleteById(Long id) {

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL);

            statement.setLong(1, id);

            log.debug("Query to database completed");

            return (statement.executeUpdate() > 0);

        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public long countAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(COUNT_ALL_SQL);

            log.debug("Query to database completed");

            if (resultSet.next())
            {
               return resultSet.getLong(1);
            }

            return 0L;

        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }

    }

    private Book process (ResultSet resultSet) throws SQLException{
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setName(resultSet.getString("name"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setCover(Book.Cover.valueOf(resultSet.getString("enum_value")));
        book.setAuthor(resultSet.getString("author"));
        book.setPublishedYear(resultSet.getInt("published_year"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }


}
