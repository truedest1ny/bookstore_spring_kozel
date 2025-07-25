package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.user.UserChangePasswordDto;
import com.kozel.bookstore.service.dto.user.UserDto;
import com.kozel.bookstore.service.dto.user.UserSessionDto;
import com.kozel.bookstore.service.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.kozel.bookstore.util.WebConstants.*;

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
                                @SessionAttribute(name = USER_ATTRIBUTE_KEY) UserSessionDto sessionDto,
                                RedirectAttributes attributes){
        user.setId(sessionDto.getId());
        user.setRole(UserUpdateDto.Role.valueOf(sessionDto.getRole().toString()));
        service.update(user);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "Changes have been applied successfully.");
        return "redirect:/profile";
    }

    @GetMapping("/edit/password")
    public String getChangePasswordForm(){
        return "user/change_password";
    }

    @PostMapping("/edit/password")
    public String changePassword(@ModelAttribute UserChangePasswordDto passwordDto,
                                 @SessionAttribute(name = USER_ATTRIBUTE_KEY) UserSessionDto userSessionDto,
                                 RedirectAttributes attributes){
        passwordDto.setId(userSessionDto.getId());
        service.changePassword(passwordDto);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "Password has been changed successfully.");
        return "redirect:/profile";
    }

    
    private void addToProfile(UserSessionDto user, Model model) {
        UserDto userDto = service.getById(user.getId());
        model.addAttribute(USER_ATTRIBUTE_KEY, userDto);
        model.addAttribute(IS_OWNER_PROFILE_ATTRIBUTE_KEY, true);
    }
}
