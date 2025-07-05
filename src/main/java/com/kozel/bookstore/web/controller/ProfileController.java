package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserSessionDto;
import com.kozel.bookstore.service.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService service;

    @GetMapping
    public String handleProfilePage(@SessionAttribute UserSessionDto user, Model model){
        addToProfile(user, model);
        return "user/user";
    }

    @GetMapping("/edit")
    public String editProfile(@SessionAttribute UserSessionDto user, Model model){
        addToProfile(user, model);
        return "user/update_user";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute UserUpdateDto user,
                                @SessionAttribute(name = "user") UserSessionDto sessionDto){
        user.setId(sessionDto.getId());
        user.setRole(UserUpdateDto.Role.valueOf(sessionDto.getRole().toString()));
        service.update(user);
        return "redirect:/profile";
    }

    @GetMapping("/edit/password")
    public String getChangePasswordForm(){
        return "user/change_password";
    }

    
    private void addToProfile(UserSessionDto user, Model model) {
        UserDto userDto = service.getById(user.getId());
        model.addAttribute("user", userDto);
        model.addAttribute("isOwnerProfile", true);
    }
}
