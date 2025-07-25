package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.user.UserCreateDto;
import com.kozel.bookstore.service.dto.user.UserDto;
import com.kozel.bookstore.service.dto.user.UserShowingDto;
import com.kozel.bookstore.service.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/add")
    public String getAddUserForm() {
        return "user/create_user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute UserCreateDto user,
                          RedirectAttributes attributes) {
        userService.create(user);
        attributes.addFlashAttribute("success",
                "The user has been successfully added!");
        return "redirect:/users";
    }

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
    public String updateUser(@PathVariable long id,
                             @ModelAttribute UserUpdateDto user,
                             RedirectAttributes attributes) {
        user.setId(id);
        userService.update(user);
        attributes.addFlashAttribute("success",
                "User data has been successfully changed.");
        return "redirect:/users/" + user.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id,
                             RedirectAttributes attributes) {
        userService.disable(id);
        attributes.addFlashAttribute("success",
                "User has been successfully disabled.");
        return "redirect:/users";
    }

    @GetMapping
    public String getUsers(Model model){
        List<UserShowingDto> users = userService.getUsersDtoShort();
        model.addAttribute("users", users);
        return "user/users";
    }
}

