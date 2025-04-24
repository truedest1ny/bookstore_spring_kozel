package com.kozel.bookstore.controller.impl.book;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.dto.ServiceBookDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller("updateBook")
@RequiredArgsConstructor
public class UpdateBookCommand implements Command {

    private final BookService bookService;

    @Override
    public CommandResult process(HttpServletRequest req) {


        ServiceBookDto book = new ServiceBookDto();

        try {
            book.setId(Long.parseLong(req.getParameter("id")));
            book.setName(req.getParameter("name").trim());
            book.setAuthor(req.getParameter("author").trim());
            book.setPublishedYear(Integer.parseInt(req.getParameter("published_year").trim()));
            book.setPrice(BigDecimal.valueOf(Double.parseDouble(req.getParameter("price").trim())));
            book.setCover(ServiceBookDto.Cover.valueOf(req.getParameter("cover")));

            ServiceBookDto savedBook = bookService.update(book);
            req.setAttribute("book", savedBook);

            return new CommandResult("jsp/book/book.jsp", HttpServletResponse.SC_OK);
        } catch (NumberFormatException e){
            throw new NumberFormatException(e.getMessage());
        }
    }

    @Override
    public CommandResult process(HttpServletRequest req, Exception e) {
       return null;
    }
}
