package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.UserChangePasswordDto;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                                @SessionAttribute(name = "user") UserSessionDto sessionDto,
                                RedirectAttributes attributes){
        user.setId(sessionDto.getId());
        user.setRole(UserUpdateDto.Role.valueOf(sessionDto.getRole().toString()));
        service.update(user);
        attributes.addFlashAttribute("success",
                "Changes have been applied successfully.");
        return "redirect:/profile";
    }

    @GetMapping("/edit/password")
    public String getChangePasswordForm(){
        return "user/change_password";
    }

    @PostMapping("/edit/password")
    public String changePassword(@ModelAttribute UserChangePasswordDto passwordDto,
                                 @SessionAttribute(name = "user") UserSessionDto userSessionDto,
                                 RedirectAttributes attributes){
        passwordDto.setId(userSessionDto.getId());
        service.changePassword(passwordDto);
        attributes.addFlashAttribute("success",
                "Password has been changed successfully.");
        return "redirect:/profile";
    }

    
    private void addToProfile(UserSessionDto user, Model model) {
        UserDto userDto = service.getById(user.getId());
        model.addAttribute("user", userDto);
        model.addAttribute("isOwnerProfile", true);
    }
}
