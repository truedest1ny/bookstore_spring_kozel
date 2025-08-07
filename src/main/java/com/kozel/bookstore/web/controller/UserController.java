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

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements PaginationValidator {

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
        model.addAttribute(USER_ATTRIBUTE_KEY, user);
        return "user/user";
    }

    @GetMapping("/edit/{id}")
    public String getEditUserForm(@PathVariable long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute(USER_ATTRIBUTE_KEY, user);
        model.addAttribute(USER_ROLES_ATTRIBUTE_KEY, UserDto.Role.values());
        return "user/update_user";
    }

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

