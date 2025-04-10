package com.kozel.bookstore.controller.impl.book;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.dto.BookDtoShowing;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BooksCommand implements Command {

    private final BookService bookService;


    @Override
    public CommandResult process(HttpServletRequest req) {

        List<BookDtoShowing> books = bookService.getBooksDtoShort();
        req.setAttribute("books", books);
        return new CommandResult("jsp/book/books.jsp", HttpServletResponse.SC_OK);

    }

    @Override
    public CommandResult process(HttpServletRequest req, Exception e) {
        return null;
    }
}

