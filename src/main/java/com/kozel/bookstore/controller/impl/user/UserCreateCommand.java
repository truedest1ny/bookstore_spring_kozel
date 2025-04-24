package com.kozel.bookstore.controller.impl.user;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.ServiceUserCreateDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("createUser")
@RequiredArgsConstructor
public class UserCreateCommand implements Command {
    private final UserService userService;

    @Override
    public CommandResult process(HttpServletRequest req) {


        ServiceUserCreateDto user = new ServiceUserCreateDto();

        try {
            user.setEmail(req.getParameter("email").trim());
            user.setLogin(req.getParameter("login").trim());
            user.setPassword(req.getParameter("password").trim());

            userService.create(user);

            return new CommandResult("index.jsp", HttpServletResponse.SC_OK);
        } catch (NumberFormatException e){
            throw new NumberFormatException(e.getMessage());
        }
    }

    @Override
    public CommandResult process(HttpServletRequest req, Exception e) {
        return null;
    }
}
