package com.kozel.bookstore.controller.impl.user;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserCreatingCommand implements Command {

    @Override
    public CommandResult process(HttpServletRequest req) {
        return new CommandResult("jsp/user/create_user.jsp", HttpServletResponse.SC_OK);
    }

    @Override
    public CommandResult process(HttpServletRequest req, Exception e) {
        return null;
    }
}
