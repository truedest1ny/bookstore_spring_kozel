package com.kozel.bookstore.controller.impl.user;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("updateUser")
@RequiredArgsConstructor
public class UpdateUserCommand implements Command {

    private final UserService userService;

    @Override
    public CommandResult process(HttpServletRequest req) {


        UserDto user = new UserDto();

        try {
            user.setId(Long.parseLong(req.getParameter("id").trim()));
            user.setFirstName(req.getParameter("first_name").trim());
            user.setLastName(req.getParameter("last_name").trim());
            user.setEmail(req.getParameter("email").trim());
            user.setLogin(req.getParameter("login").trim());
            user.setPassword(req.getParameter("password").trim());
            user.setRole(UserDto.Role.valueOf(req.getParameter("role").trim()));

            userService.update(user);

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
