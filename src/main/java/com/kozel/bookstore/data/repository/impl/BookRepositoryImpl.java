package com.kozel.bookstore.data.repository.impl;

import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private static final String GET_ALL =
            "SELECT b FROM Book b";
    private static final String DELETE =
            "DELETE FROM Book";
    private static final String COUNT_ALL =
            "SELECT COUNT(b) from Book b";
    private static final String GET_BY_AUTHOR =
            "SELECT b FROM Book b WHERE b.author = :author";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, false);

        Optional<Book> book = Optional.ofNullable(
                session.find(Book.class, isbn));

        disableDeletedFilter(session);
        return book;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, false);

        List<Book> books = session.createQuery(GET_BY_AUTHOR, Book.class)
                            .setParameter("author", author)
                            .getResultList();

        disableDeletedFilter(session);
        return books;
    }

    @Override
    public long countAll() {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, false);

        long result = session.createQuery(COUNT_ALL, Long.class).getSingleResult();

        disableDeletedFilter(session);
        return result;
    }

    @Override
    public void clearDeletedRows() {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, true);

        session.createQuery(DELETE, Book.class).executeUpdate();

        disableDeletedFilter(session);
    }

    @Override
    public Book save(Book book) {
        if (book.getId() != null){
            manager.merge(book);
        }
        else {
            manager.persist(book);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, false);

        Optional<Book> book = Optional.ofNullable(
                session.find(Book.class, id));

        disableDeletedFilter(session);
        return book;
    }

    @Override
    public List<Book> findAll() {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, false);

        List<Book> books = session.createQuery(GET_ALL, Book.class).getResultList();

        disableDeletedFilter(session);

        return books;
    }


    @Override
    public void delete(Book book) {
        book.setDeleted(true);
        manager.merge(book);
    }

}
