package com.kozel.bookstore.controller.impl.order;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.OrderService;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.ServiceOrderShowingDto;
import com.kozel.bookstore.service.dto.ServiceUserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("ordersByUser")
@RequiredArgsConstructor
public class OrdersByUserCommand implements Command {

    private final OrderService orderService;
    private final UserService userService;

    @Override
    public CommandResult process(HttpServletRequest req) {
        String login = req.getParameter("userFilter");
        ServiceUserDto userDto = userService.getByLogin(login);


        List<ServiceOrderShowingDto> orders = orderService.findByUserId(userDto.getId());
        req.setAttribute("orders", orders);
        req.setAttribute("login", login);
        return new CommandResult("jsp/order/orders_by_user.jsp", HttpServletResponse.SC_OK);
    }
}
