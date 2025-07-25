package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.dto.book.BookDto;
import com.kozel.bookstore.service.dto.book.BookShowingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.kozel.bookstore.util.WebConstants.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {


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
                             RedirectAttributes attributes) {
        service.disable(id);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "The book has been successfully deleted.");
        return "redirect:/books";
    }

    @GetMapping
    public String getBooks(Model model) {
        List<BookShowingDto> books = service.getBooksDtoShort();
        model.addAttribute(BOOKS_ATTRIBUTE_KEY, books);
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






