package com.kozel.bookstore.controller.impl.book;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.dto.ServiceBookDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;

@Controller("creatingBook")
public class BookCreatingCommand implements Command {

    @Override
    public CommandResult process(HttpServletRequest req) {

        req.setAttribute("covers", ServiceBookDto.Cover.values());

        return new CommandResult("jsp/book/create_book.jsp", HttpServletResponse.SC_OK);
    }
}
