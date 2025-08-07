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

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements PaginationValidator {

    private final BookService service;

    @GetMapping("/{id}")
    public String getBook(@PathVariable long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute(BOOK_ATTRIBUTE_KEY, book);
        return "book/book";
    }

    @GetMapping("/add")
    public String getBookAddingForm(Model model) {
        model.addAttribute(BOOK_COVERS_ATTRIBUTE_KEY, BookDto.Cover.values());
        return "book/create_book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute BookDto book,
                          RedirectAttributes attributes) {
        BookDto savedBook = service.create(book);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "The book has been successfully added! It is now available in the catalog.");
        return "redirect:/books/" + savedBook.getId();
    }

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

    @GetMapping("/edit/{id}")
    public String getUserEditForm(@PathVariable long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute(BOOK_ATTRIBUTE_KEY, book);
        model.addAttribute(BOOK_COVERS_ATTRIBUTE_KEY, BookDto.Cover.values());
        return "book/update_book";

    }

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







