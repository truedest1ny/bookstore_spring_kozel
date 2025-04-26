package com.kozel.bookstore.controller.impl.order;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.ServiceUserShowingDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("filterOrdersByUser")
@RequiredArgsConstructor
public class FilterOrdersByUserCommand implements Command {

    private final UserService userService;

    @Override
    public CommandResult process(HttpServletRequest req) {

        List<ServiceUserShowingDto> users = userService.getUsersDtoShort();
        req.setAttribute("users", users);
        return new CommandResult("jsp/order/filter_orders_by_user.jsp", HttpServletResponse.SC_OK);
    }
}
