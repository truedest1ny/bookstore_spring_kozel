package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.repository.annotation.QueryDeletedFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @QueryDeletedFilter(isDeleted = false)
    Optional<Book> findByIsbn(String isbn);

    @QueryDeletedFilter(isDeleted = false)
    List<Book> findByAuthor(String author);

    @Query("SELECT b FROM Book b WHERE b.id IN :ids")
    @QueryDeletedFilter(isDeleted = false)
    List<Book> findAllByIds(Collection<Long> ids);

    @QueryDeletedFilter(isDeleted = false)
    long count();

    @Modifying
    @Query("DELETE FROM Book b WHERE b.isDeleted = true")
    void clearDeletedRows();

    @NonNull
    @QueryDeletedFilter(isDeleted = false)
    @Query("SELECT b FROM Book b WHERE b.id = :id")
    Optional<Book> findById(@NonNull Long id);

    @NonNull
    @QueryDeletedFilter(isDeleted = false)
    Page<Book> findAll(@NonNull Pageable pageable);

    @Modifying
    @Query("UPDATE Book b SET b.isDeleted = true WHERE b.id = :id")
    void softDelete(Long id);

}
