package com.kozel.bookstore.controller.impl.book;
import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.dto.ServiceBookDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("book")
@RequiredArgsConstructor
public class BookCommand implements Command {

    private final BookService bookService;


    @Override
    public CommandResult process(HttpServletRequest req, Exception e) {
        return null;
    }

    @Override
    public CommandResult process(HttpServletRequest req){

        long id = Long.parseLong(req.getParameter("id"));
        ServiceBookDto book = bookService.getById(id);
        req.setAttribute("book", book);

        return new CommandResult("jsp/book/book.jsp", HttpServletResponse.SC_OK);
        }
    }





