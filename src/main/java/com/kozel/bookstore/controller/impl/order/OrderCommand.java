package com.kozel.bookstore.controller.impl.order;


import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.OrderService;
import com.kozel.bookstore.service.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("order")
@RequiredArgsConstructor
public class OrderCommand implements Command {
    private final OrderService orderService;

    @Override
    public CommandResult process(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));
        OrderDto order = orderService.getById(id);
        req.setAttribute("order", order);
        req.setAttribute("items", order.getItems());
        return new CommandResult("jsp/order/order.jsp", HttpServletResponse.SC_OK);
    }
}
