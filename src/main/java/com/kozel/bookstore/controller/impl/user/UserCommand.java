package com.kozel.bookstore.controller.impl.user;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserCommand implements Command {

        private final UserService userService;


    @Override
        public CommandResult process(HttpServletRequest req){
            long id = Long.parseLong(req.getParameter("id"));
            UserDto user = userService.getById(id);
            req.setAttribute("user", user);
        return new CommandResult("jsp/user/user.jsp", HttpServletResponse.SC_OK);

        }

    @Override
    public CommandResult process(HttpServletRequest req, Exception e) {
            return null;
    }
}

