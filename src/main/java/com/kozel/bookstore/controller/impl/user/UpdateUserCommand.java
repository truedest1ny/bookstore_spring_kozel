package com.kozel.bookstore.controller.impl.user;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.ServiceUserDto;
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
        ServiceUserDto user = new ServiceUserDto();

        try {
            user.setId(Long.parseLong(req.getParameter("id").trim()));
            user.setFirstName(req.getParameter("first_name").trim());
            user.setLastName(req.getParameter("last_name").trim());
            user.setEmail(req.getParameter("email").trim());
            user.setPassword(req.getParameter("password").trim());
            user.setRole(ServiceUserDto.Role.valueOf(req.getParameter("role").trim()));

            ServiceUserDto savedUser = userService.update(user);
            req.setAttribute("user", savedUser);

            return new CommandResult("jsp/user/user.jsp", HttpServletResponse.SC_OK);
        } catch (NumberFormatException e){
            throw new NumberFormatException(e.getMessage());
        }
    }

    @Override
    public CommandResult process(HttpServletRequest req, Exception e) {
        return null;
    }
}
