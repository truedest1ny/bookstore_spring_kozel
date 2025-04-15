package com.kozel.bookstore.data.dao.impl;

import com.kozel.bookstore.data.dao.BookDao;
import com.kozel.bookstore.data.entity.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private static final String ADD_SQL_NP =
            "INSERT INTO books (name, isbn, cover_id, author, published_year, price) " +
                    "VALUES (:name, :isbn, (SELECT id FROM covers WHERE enum_value = :cover), :author, :publishedYear, :price)";

    private static final String GET_BY_ID_SQL_NP =
            "SELECT bk.id, bk.name, bk.isbn, cv.enum_value, bk.author, bk.published_year, bk.price FROM books bk" +
                    " JOIN covers cv ON bk.cover_id = cv.id" +
                    " WHERE bk.id = :id AND bk.is_deleted = false";

    private static final String GET_ALL_SQL =
            "SELECT bk.id, bk.name, bk.isbn, cv.enum_value, bk.author, bk.published_year, bk.price FROM books bk" +
                    " JOIN covers cv ON bk.cover_id = cv.id" +
                    " WHERE bk.is_deleted = false" +
                    " ORDER BY bk.id";

    private static final String GET_BY_ISBN_SQL_NP =
            "SELECT bk.id, bk.name, bk.isbn, cv.enum_value, bk.author, bk.published_year, bk.price FROM books bk" +
                    " JOIN covers cv ON bk.cover_id = cv.id" +
                    " WHERE bk.isbn = :isbn AND bk.is_deleted = false";

    private static final String GET_BY_AUTHOR_SQL_NP =
            "SELECT bk.id, bk.name, bk.isbn, cv.enum_value, bk.author, bk.published_year, bk.price FROM books bk" +
                    " JOIN covers cv ON bk.cover_id = cv.id" +
                    " WHERE bk.author = :author AND bk.is_deleted = false";

    private static final String UPDATE_SQL_NP =
            "UPDATE books SET " +
                    "name = :name, " +
                    "isbn = :isbn, " +
                    "cover_id = (SELECT id FROM covers WHERE enum_value = :cover), " +
                    "author = :author, " +
                    "published_year = :publishedYear, " +
                    "price = :price, " +
                    "is_deleted = :isDeleted " +
                    "WHERE id = :id";

    private static final String CLEAR_DELETED_ROWS_SQL_NP =
            "DELETE FROM books WHERE is_deleted = :isDeleted";

    private static final String COUNT_ALL_SQL =
            "SELECT COUNT (id) FROM books";

    private final NamedParameterJdbcTemplate template;



    @Override
    public Long save(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", book.getName())
                .addValue("isbn", book.getIsbn())
                .addValue("cover", book.getCover().toString())
                .addValue("author", book.getAuthor())
                .addValue("publishedYear", book.getPublishedYear())
                .addValue("price", book.getPrice());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(ADD_SQL_NP, parameterSource, keyHolder);

        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    public List<Book> findAll() {
        return template.query(GET_ALL_SQL, this::mapRow);
    }


    @Override
    public Book findById(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        return template.queryForObject(GET_BY_ID_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public Book findByIsbn(String isbn) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("isbn", isbn);

        return template.queryForObject(GET_BY_ISBN_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", author);

        return template.query(GET_BY_AUTHOR_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public Book update(Book book) {
        MapSqlUpdate(book);
        return findById(book.getId());
    }

    @Override
    public void delete(Book book) {
        MapSqlUpdate(book);
    }

    @Override
    public long clearDeletedRows() {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("isDeleted", true);

        return template.update(CLEAR_DELETED_ROWS_SQL_NP, parameterSource);
    }

    @Override
    public long countAll() {
        return template.queryForObject(COUNT_ALL_SQL, new HashMap<>(), Long.class);
    }

    private Book mapRow(ResultSet resultSet, int rowNum) throws SQLException{
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
    private void MapSqlUpdate(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", book.getId())
                .addValue("name", book.getName())
                .addValue("isbn", book.getIsbn())
                .addValue("cover", book.getCover().toString())
                .addValue("author", book.getAuthor())
                .addValue("publishedYear", book.getPublishedYear())
                .addValue("price", book.getPrice())
                .addValue("isDeleted", book.isDeleted());

        template.update(UPDATE_SQL_NP, parameterSource);
    }
}



