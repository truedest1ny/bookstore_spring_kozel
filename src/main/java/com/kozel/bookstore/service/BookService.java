package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.book.BookDto;
import com.kozel.bookstore.service.dto.book.BookShowingDto;
import com.kozel.bookstore.service.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * A service for managing book-related business logic.
 * This service provides methods for retrieving, creating, updating,
 * and managing the lifecycle of book entities.
 */
public interface BookService {

    /**
     * Retrieves a page of all books, including detailed information.
     * This method is suitable for administrative purposes.
     *
     * @param pageable The pagination information.
     * @return A list of all books as {@link BookDto}.
     */
    Page<BookDto> getAll(Pageable pageable);

    /**
     * Retrieves a paginated list of books with a simplified view.
     *
     * @param pageable The pagination information.
     * @return A paginated list of books as {@link BookShowingDto}.
     */
    Page<BookShowingDto> getBooksDtoShort(Pageable pageable);

    /**
     * Retrieves a single book by its unique identifier.
     *
     * @param id The unique ID of the book.
     * @return The {@link BookDto} for the specified ID.
     * @throws ResourceNotFoundException if no book is found with the given ID.
     */
    BookDto getById(Long id);

    /**
     * Creates a new book in the database.
     *
     * @param bookDto The DTO containing the book data to be created.
     * @return The newly created {@link BookDto} with the assigned ID.
     */
    BookDto create(BookDto bookDto);

    /**
     * Updates an existing book's information.
     * The ID in the provided DTO must match an existing book.
     *
     * @param bookDto The DTO containing the updated book data.
     * @return The updated {@link BookDto}.
     * @throws IllegalArgumentException if the DTO's ID is null.
     * @throws ResourceNotFoundException if no book is found with the specified ID.
     */
    BookDto update(BookDto bookDto);

    /**
     * Performs a soft delete on a book.
     * The book is not physically removed from the database; its 'isDeleted' flag is set to true.
     *
     * @param id The unique ID of the book to be soft-deleted.
     * @throws ResourceNotFoundException if no book is found with the specified ID.
     */
    void disable(Long id);

    /**
     * Calculates the total price of all books by a specific author.
     *
     * @param author The author's name.
     * @return The sum of the prices of all books by the author, or {@link BigDecimal#ZERO} if no books are found.
     */
    BigDecimal getSumPriceByAuthor (String author);

}
