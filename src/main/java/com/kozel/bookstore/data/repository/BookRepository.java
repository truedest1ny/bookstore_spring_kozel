package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.aspect.QueryFilterAspect;
import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.repository.annotation.QueryDeletedFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * A Spring Data JPA repository for the {@link Book} entity.
 * This repository handles all data access operations related to books,
 * with most query methods automatically filtered to exclude soft-deleted
 * entities via the {@link QueryFilterAspect}.
 *
 * @see Book
 * @see QueryDeletedFilter
 * @see QueryFilterAspect
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Finds a single book by its unique ISBN.
     * The {@link QueryDeletedFilter} annotation ensures that this method
     * only returns a book that has not been soft-deleted.
     *
     * @param isbn The ISBN of the book to find.
     * @return An {@link Optional} containing the book, or empty if not found or if the book is deleted.
     */
    @QueryDeletedFilter(isDeleted = false)
    Optional<Book> findByIsbn(String isbn);

    /**
     * Finds a list of books by a specific author.
     * The {@link QueryDeletedFilter} annotation ensures that only non-deleted
     * books are included in the result list.
     *
     * @param author The author's name to search for.
     * @return A {@link List} of books by the given author.
     */
    @QueryDeletedFilter(isDeleted = false)
    List<Book> findByAuthor(String author);

    /**
     * Finds a list of books by a collection of their IDs.
     * The {@link QueryDeletedFilter} annotation ensures that only non-deleted
     * books matching the given IDs are returned.
     *
     * @param ids A collection of book IDs to search for.
     * @return A {@link List} of books matching the provided IDs.
     */
    @Query("SELECT b FROM Book b WHERE b.id IN :ids")
    @QueryDeletedFilter(isDeleted = false)
    List<Book> findAllByIds(Collection<Long> ids);

    /**
     * Counts the total number of active (non-deleted) books in the repository.
     * The {@link QueryDeletedFilter} ensures that soft-deleted rows are not counted.
     *
     * @return The total count of active books.
     */
    @QueryDeletedFilter(isDeleted = false)
    long count();

    /**
     * Permanently deletes all soft-deleted book rows from the database.
     * This is a "hard" delete operation and should be used with caution.
     * This method does not use the soft-delete filter, as its purpose is to
     * specifically operate on deleted records.
     */
    @Modifying
    @Query("DELETE FROM Book b WHERE b.isDeleted = true")
    void clearDeletedRows();

    /**
     * Finds a book by its unique ID.
     * The {@link QueryDeletedFilter} annotation ensures that only a non-deleted
     * book will be returned.
     *
     * @param id The ID of the book to find.
     * @return An {@link Optional} containing the book, or empty if the book is not found or is deleted.
     */
    @NonNull
    @QueryDeletedFilter(isDeleted = false)
    @Query("SELECT b FROM Book b WHERE b.id = :id")
    Optional<Book> findById(@NonNull Long id);

    /**
     * Returns a {@link Page} of all active (non-deleted) books.
     * The {@link QueryDeletedFilter} automatically filters out soft-deleted books.
     *
     * @param pageable The pagination information.
     * @return A page of active books.
     */
    @NonNull
    @QueryDeletedFilter(isDeleted = false)
    Page<Book> findAll(@NonNull Pageable pageable);

    /**
     * Performs a "soft" delete on a book by setting its {@code isDeleted} flag to {@code true}.
     * The record is not physically removed from the database.
     *
     * @param id The ID of the book to soft-delete.
     */
    @Modifying
    @Query("UPDATE Book b SET b.isDeleted = true WHERE b.id = :id")
    void softDelete(Long id);

}
