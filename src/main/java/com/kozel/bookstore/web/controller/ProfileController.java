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

/**
 * Controller for handling user profile management.
 * This class provides endpoints for viewing, editing, and updating a user's
 * profile information, including their password.
 */
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService service;

    /**
     * Displays the user's profile page.
     * This method fetches the full user details and adds them to the model,
     * along with a flag indicating that this is the profile of the logged-in user.
     *
     * @param user The session attribute containing the logged-in user's details.
     * @param model The Spring Model for adding attributes.
     * @return The view name for the user profile page.
     */
    @GetMapping
    public String handleProfilePage(@SessionAttribute UserSessionDto user, Model model){
        addToProfile(user, model);
        return "user/user";
    }

    /**
     * Displays the form for editing the user's profile.
     * It adds the user's current data to the model to pre-populate the form fields.
     *
     * @param user The session attribute containing the logged-in user's details.
     * @param model The Spring Model for adding attributes.
     * @return The view name for the profile update form.
     */
    @GetMapping("/edit")
    public String editProfile(@SessionAttribute UserSessionDto user, Model model){
        addToProfile(user, model);
        return "user/update_user";
    }

    /**
     * Handles the submission of the profile update form.
     * This method updates the user's information and redirects to the profile page
     * with a success message.
     *
     * @param user The UserUpdateDto containing the updated profile data.
     * @param sessionDto The session attribute for the current user.
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the profile page.
     */
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

    /**
     * Displays the form for changing the user's password.
     *
     * @return The view name for the change password form.
     */
    @GetMapping("/edit/password")
    public String getChangePasswordForm(){
        return "user/change_password";
    }

    /**
     * Handles the submission of the change password form.
     * This method updates the user's password and redirects to the profile page
     * with a success message.
     *
     * @param passwordDto The UserChangePasswordDto containing the new and old passwords.
     * @param userSessionDto The session attribute for the current user.
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the profile page.
     */
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

    /**
     * Helper method to add user profile attributes to the model.
     *
     * @param user The session user.
     * @param model The Spring Model.
     */
    private void addToProfile(UserSessionDto user, Model model) {
        UserDto userDto = service.getById(user.getId());
        model.addAttribute(USER_ATTRIBUTE_KEY, userDto);
        model.addAttribute(IS_OWNER_PROFILE_ATTRIBUTE_KEY, true);
    }
}
