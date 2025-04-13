package com.kozel.bookstore.controller.impl.user;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("deleteUser")
@RequiredArgsConstructor
public class UserDeleteCommand implements Command {

    private final UserService userService;

    @Override
    public CommandResult process(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));

        userService.disable(id);

        return new CommandResult("index.jsp", HttpServletResponse.SC_OK);
    }

    @Override
    public CommandResult process(HttpServletRequest req, Exception e) {
        return null;
    }
}
