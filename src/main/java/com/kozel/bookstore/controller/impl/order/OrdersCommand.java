package com.kozel.bookstore.controller.impl.order;


import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.OrderService;
import com.kozel.bookstore.service.dto.ServiceOrderShowingDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("orders")
@RequiredArgsConstructor
public class OrdersCommand implements Command {

    private final OrderService orderService;

    @Override
    public CommandResult process(HttpServletRequest req) {
        List<ServiceOrderShowingDto> orders = orderService.getOrdersDtoShort();
        req.setAttribute("orders", orders);
        return new CommandResult("jsp/order/orders.jsp", HttpServletResponse.SC_OK);
    }
}
