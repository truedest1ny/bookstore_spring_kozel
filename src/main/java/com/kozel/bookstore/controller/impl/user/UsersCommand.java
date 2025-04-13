package com.kozel.bookstore.controller.impl.user;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.UserDtoShowing;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("users")
@RequiredArgsConstructor
public class UsersCommand implements Command {

    private final UserService userService;


    @Override
    public CommandResult process(HttpServletRequest req){

        List<UserDtoShowing> users = userService.getUsersDtoShort();
        req.setAttribute("users", users);
        return new CommandResult("jsp/user/users.jsp", HttpServletResponse.SC_OK);
    }

    @Override
    public CommandResult process(HttpServletRequest req, Exception e) {
        return null;
    }
}
