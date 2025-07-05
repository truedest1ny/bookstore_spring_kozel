package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserShowingDto;
import com.kozel.bookstore.service.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public String getUser(@PathVariable long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/edit/{id}")
    public String getEditUserForm(@PathVariable long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", UserDto.Role.values());

        return "user/update_user";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable long id, @ModelAttribute UserUpdateDto user) {
        user.setId(id);
        userService.update(user);
        return "redirect:/users/" + user.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.disable(id);
        return "redirect:/users";
    }

    @GetMapping
    public String getUsers(Model model){
        List<UserShowingDto> users = userService.getUsersDtoShort();
        model.addAttribute("users", users);
        return "user/users";
    }
}

