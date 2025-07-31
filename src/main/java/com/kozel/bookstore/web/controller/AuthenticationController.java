package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.service.CartService;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.cart.CartDto;
import com.kozel.bookstore.service.dto.user.UserCreateDto;
import com.kozel.bookstore.service.dto.user.UserDto;
import com.kozel.bookstore.service.dto.user.UserLoginDto;
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

import static com.kozel.bookstore.util.WebConstants.*;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final DataMapper mapper;
    private final UserService userService;
    private final CartService cartService;

    @GetMapping("/login")
    public String getLoginPage(HttpSession session, Model model){
        model.addAttribute(WARN_MESSAGE_KEY, session.getAttribute("loginMessage"));
        session.removeAttribute(WARN_MESSAGE_KEY);

        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDto user,
                        HttpServletRequest request,
                        HttpSession session){
        UserDto userDto = userService.login(user);
        CartDto cart = (CartDto) session.getAttribute(CART_ATTRIBUTE_KEY);

        request.changeSessionId();

        CartDto mergedCart = cartService.mergeCartToUser(userDto.getId(), cart);
        session.setAttribute(USER_ATTRIBUTE_KEY, mapper.toSessionDto(userDto));
        session.setAttribute(CART_ATTRIBUTE_KEY, mergedCart);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes attributes){
        session.removeAttribute(USER_ATTRIBUTE_KEY);
        session.removeAttribute(CART_ATTRIBUTE_KEY);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "You have successfully signed out.");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegisterForm() {
        return "user/create_user";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserCreateDto user,
                           RedirectAttributes attributes) {
        userService.create(user);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "You have successfully registered!" +
                        " To access the system, use the parameters you specified.");
        return "redirect:/";
    }


}
