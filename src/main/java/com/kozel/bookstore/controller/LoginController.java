package com.kozel.bookstore.controller;

import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserLoginDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final DataMapper mapper;

    private final UserService service;

    @GetMapping("/login")
    public String getLoginPage(){
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDto user, HttpSession session){
        UserDto userDto = service.login(user);
        session.setAttribute("user", mapper.toSessionDto(userDto));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        service.logout(session);
        return "redirect:/";
    }
}
