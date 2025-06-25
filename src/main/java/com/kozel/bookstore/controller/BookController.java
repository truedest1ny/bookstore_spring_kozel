package com.kozel.bookstore.controller;

import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.dto.BookDto;
import com.kozel.bookstore.service.dto.BookShowingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping("/{id}")
    public String getBook(@PathVariable long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        return "book/book";
    }

    @GetMapping("/add")
    public String getBookAddingForm(Model model) {
        model.addAttribute("covers", BookDto.Cover.values());
        return "book/create_book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute BookDto book) {
        BookDto savedBook = service.create(book);
        return "redirect:/books/" + savedBook.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        service.disable(id);
        return "redirect:/books";
    }

    @GetMapping
    public String getBooks(Model model) {
        List<BookShowingDto> books = service.getBooksDtoShort();
        model.addAttribute("books", books);
        return "book/books";
    }

    @GetMapping("/edit/{id}")
    public String getUserEditForm(@PathVariable long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("covers", BookDto.Cover.values());
        return "book/update_book";

    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable long id, @ModelAttribute BookDto book) {
        book.setId(id);
        service.update(book);
        return "redirect:/books/" + book.getId();
    }
}






