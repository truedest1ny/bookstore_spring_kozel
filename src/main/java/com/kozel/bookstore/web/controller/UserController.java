package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.user.UserCreateDto;
import com.kozel.bookstore.service.dto.user.UserDto;
import com.kozel.bookstore.service.dto.user.UserShowingDto;
import com.kozel.bookstore.service.dto.user.UserUpdateDto;
import com.kozel.bookstore.web.pagination.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import static com.kozel.bookstore.util.WebConstants.*;

/**
 * Controller for managing users from an administrative perspective.
 * This class provides endpoints for viewing a list of users, adding new users,
 * updating existing user information, and disabling user accounts.
 */
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements PaginationValidator {

    private final UserService userService;

    /**
     * Displays the form for adding a new user.
     *
     * @return The view name for the user creation form.
     */
    @GetMapping("/add")
    public String getAddUserForm() {
        return "user/create_user";
    }

    /**
     * Handles the submission of the new user form.
     * This method creates a new user and redirects to the main users list with a success message.
     *
     * @param user The UserCreateDto containing the details of the new user.
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the users list page.
     */
    @PostMapping("/add")
    public String addUser(@ModelAttribute UserCreateDto user,
                          RedirectAttributes attributes) {
        userService.create(user);
        attributes.addFlashAttribute("success",
                "The user has been successfully added!");
        return "redirect:/users";
    }

    /**
     * Displays a single user's details page.
     * This method fetches a user by their unique ID and adds it to the model for the view.
     *
     * @param id The unique ID of the user.
     * @param model The Spring Model for adding attributes.
     * @return The view name for the user details page.
     */
    @GetMapping("/{id}")
    public String getUser(@PathVariable long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute(USER_ATTRIBUTE_KEY, user);
        return "user/user";
    }

    /**
     * Displays the form for editing an existing user.
     * This method fetches the user's details and a list of available roles to pre-populate the form.
     *
     * @param id The unique ID of the user to edit.
     * @param model The Spring Model for adding attributes.
     * @return The view name for the user update form.
     */
    @GetMapping("/edit/{id}")
    public String getEditUserForm(@PathVariable long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute(USER_ATTRIBUTE_KEY, user);
        model.addAttribute(USER_ROLES_ATTRIBUTE_KEY, UserDto.Role.values());
        return "user/update_user";
    }

    /**
     * Handles the submission of the user update form.
     * This method updates an existing user's details and redirects to their details page
     * with a success message.
     *
     * @param id The unique ID of the user being updated.
     * @param user The UserUpdateDto with the updated details.
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the updated user's page.
     */
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable long id,
                             @ModelAttribute UserUpdateDto user,
                             RedirectAttributes attributes) {
        user.setId(id);
        userService.update(user);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "User data has been successfully changed.");
        return "redirect:/users/" + user.getId();
    }

    /**
     * Disables a user account.
     * This method performs a soft delete on a user and redirects the user to the
     * main users list, preserving the current pagination state.
     *
     * @param id The unique ID of the user to disable.
     * @param page The current page number for redirection.
     * @param size The current page size for redirection.
     * @param sort The current sort parameters for redirection.
     * @param attributes RedirectAttributes to add flash messages and pagination parameters.
     * @return A redirect string to the users list page.
     */
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id,
                             @RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer size,
                             @RequestParam(required = false) List<String> sort,
                             RedirectAttributes attributes) {
        userService.disable(id);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "User has been successfully disabled.");
        addPaginationAttributes(attributes, page, size, sort);
        return "redirect:/users";
    }

    /**
     * Displays a paginated list of all users.
     * This method retrieves a page of users, corrects the pagination parameters,
     * and handles redirection if the requested page is out of bounds.
     *
     * @param pageable The pagination and sorting parameters provided by the request.
     * @param model The Spring Model for adding attributes.
     * @param attributes RedirectAttributes for redirection handling.
     * @return The view name for the users list page.
     */
    @GetMapping
    public String getUsers(@PageableDefault(
                            size = 20,
                            sort = "login",
                            direction = Sort.Direction.ASC)
                                Pageable pageable,
                                Model model,
                                RedirectAttributes attributes){

        Pageable correctedPageable = correctPageableParams(pageable, USER_SORT_PROPERTIES);
        Page<UserShowingDto> usersPage = userService.getUsersDtoShort(correctedPageable);

        Optional<String> redirectUrl = validateAndRedirectPage(
                usersPage, correctedPageable, "/users", attributes);

        if (redirectUrl.isPresent()) {
            return redirectUrl.get();
        }
        model.addAttribute(PAGE_ATTRIBUTE_KEY, usersPage);
        addSortParamsToModel(model, usersPage);
        return "user/users";
    }
}

