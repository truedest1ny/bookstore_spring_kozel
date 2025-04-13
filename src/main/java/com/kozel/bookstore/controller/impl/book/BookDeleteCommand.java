package com.kozel.bookstore.controller.impl.book;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("deleteBook")
@RequiredArgsConstructor
public class BookDeleteCommand implements Command {

    private final BookService bookService;

    @Override
    public CommandResult process(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));

        bookService.delete(id);

        return new CommandResult("index.jsp", HttpServletResponse.SC_OK);
    }

    @Override
    public CommandResult process(HttpServletRequest req, Exception e) {
        return null;
    }
}
