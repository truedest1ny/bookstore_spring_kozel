package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.dto.book.BookDto;
import com.kozel.bookstore.service.dto.book.BookShowingDto;
import com.kozel.bookstore.web.pagination.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import static com.kozel.bookstore.util.WebConstants.*;

/**
 * Controller for managing books in the bookstore.
 * This class provides endpoints for viewing a single book, listing all books
 * with pagination, and performing administrative actions like adding,
 * updating, and deleting books.
 */
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements PaginationValidator {

    private final BookService service;

    /**
     * Displays a single book's details page.
     * This method fetches a book by its unique ID and adds it to the model for the view.
     *
     * @param id The unique ID of the book.
     * @param model The Spring Model for adding attributes.
     * @return The view name for the book details page.
     */
    @GetMapping("/{id}")
    public String getBook(@PathVariable long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute(BOOK_ATTRIBUTE_KEY, book);
        return "book/book";
    }

    /**
     * Displays the form for adding a new book.
     * This method adds a list of available book covers to the model to populate a form field.
     *
     * @param model The Spring Model for adding attributes.
     * @return The view name for the book creation form.
     */
    @GetMapping("/add")
    public String getBookAddingForm(Model model) {
        model.addAttribute(BOOK_COVERS_ATTRIBUTE_KEY, BookDto.Cover.values());
        return "book/create_book";
    }

    /**
     * Handles the submission of the new book form.
     * This method creates a new book and redirects to the newly created book's details page
     * with a success message.
     *
     * @param book The BookDto containing the details of the new book.
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the new book's page.
     */
    @PostMapping("/add")
    public String addBook(@ModelAttribute BookDto book,
                          RedirectAttributes attributes) {
        BookDto savedBook = service.create(book);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "The book has been successfully added! It is now available in the catalog.");
        return "redirect:/books/" + savedBook.getId();
    }

    /**
     * Deletes (disables) a book from the system.
     * This method performs a soft delete on a book and redirects the user to the
     * main books list, preserving the current pagination state.
     *
     * @param id The unique ID of the book to delete.
     * @param page The current page number for redirection.
     * @param size The current page size for redirection.
     * @param sort The current sort parameters for redirection.
     * @param attributes RedirectAttributes to add flash messages and pagination parameters.
     * @return A redirect string to the books list page.
     */
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id,
                             @RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer size,
                             @RequestParam(required = false) List<String> sort,
                             RedirectAttributes attributes) {
        service.disable(id);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "The book has been successfully deleted.");
        addPaginationAttributes(attributes, page, size, sort);
        return "redirect:/books";
    }

    /**
     * Displays a paginated list of all books.
     * This method retrieves a page of books, corrects the pagination parameters,
     * and handles redirection if the requested page is out of bounds.
     *
     * @param pageable The pagination and sorting parameters provided by the request.
     * @param model The Spring Model for adding attributes.
     * @param attributes RedirectAttributes for redirection handling.
     * @return The view name for the books list page.
     */
    @GetMapping
    public String getBooks(@PageableDefault(
                                size = 20,
                                sort = "name",
                                direction = Sort.Direction.ASC)
                                Pageable pageable, Model model, RedirectAttributes attributes) {

        Pageable correctedPageable = correctPageableParams(pageable, BOOK_SORT_PROPERTIES);

        Page<BookShowingDto> booksPage = service.getBooksDtoShort(correctedPageable);

        Optional<String> redirectUrl = validateAndRedirectPage(
                booksPage, correctedPageable, "/books", attributes);

        if (redirectUrl.isPresent()) {
            return redirectUrl.get();
        }
        
        model.addAttribute(PAGE_ATTRIBUTE_KEY, booksPage);
        addSortParamsToModel(model, booksPage);
        return "book/books";
    }


    /**
     * Displays the form for editing an existing book.
     * This method fetches the book's details and a list of available covers to pre-populate the form.
     *
     * @param id The unique ID of the book to edit.
     * @param model The Spring Model for adding attributes.
     * @return The view name for the book update form.
     */
    @GetMapping("/edit/{id}")
    public String getUserEditForm(@PathVariable long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute(BOOK_ATTRIBUTE_KEY, book);
        model.addAttribute(BOOK_COVERS_ATTRIBUTE_KEY, BookDto.Cover.values());
        return "book/update_book";

    }

    /**
     * Handles the submission of the book update form.
     * This method updates an existing book's details and redirects to its details page
     * with a success message.
     *
     * @param id The unique ID of the book being updated.
     * @param book The BookDto with the updated details.
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the updated book's page.
     */
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable long id,
                             @ModelAttribute BookDto book,
                             RedirectAttributes attributes) {
        book.setId(id);
        service.update(book);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "Changes have been applied successfully.");
        return "redirect:/books/" + book.getId();
    }
}







