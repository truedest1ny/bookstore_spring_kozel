package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.UserCreateDto;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final DataMapper mapper;
    private final UserService service;

    @GetMapping("/login")
    public String getLoginPage(HttpSession session, Model model){
        model.addAttribute("warn", session.getAttribute("loginMessage"));
        session.removeAttribute("warn");
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDto user, HttpServletRequest request){
        UserDto userDto = service.login(user);
        HttpSession session = request.getSession();
        session.setAttribute("user", mapper.toSessionDto(userDto));
        request.changeSessionId();
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        service.logout(session);
        return "redirect:/";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserCreateDto user,
                           RedirectAttributes attributes) {
        service.create(user);
        attributes.addFlashAttribute("success",
                "You have successfully registered!" +
                        " To access the system, use the parameters you specified.");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegisterForm() {
        return "user/create_user";
    }
}
