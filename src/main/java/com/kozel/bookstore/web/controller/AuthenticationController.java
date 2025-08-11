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

/**
 * Controller for handling user authentication and registration.
 * This class manages the login, logout, and user registration processes,
 * including session management and cart merging for authenticated users.
 */
@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final DataMapper mapper;
    private final UserService userService;
    private final CartService cartService;

    /**
     * Displays the login page.
     * This method retrieves a login message from the session (if present)
     * and displays it to the user, then removes it to prevent it from
     * being shown on subsequent page loads.
     *
     * @param session The HttpSession object.
     * @param model The Spring Model to add the login message to.
     * @return The view name for the login page.
     */
    @GetMapping("/login")
    public String getLoginPage(HttpSession session, Model model){
        model.addAttribute(WARN_MESSAGE_KEY, session.getAttribute("loginMessage"));
        session.removeAttribute(WARN_MESSAGE_KEY);

        return "user/login";
    }

    /**
     * Handles the user login process.
     * Upon successful authentication, this method retrieves the user's cart from the session
     * and merges it with their permanent cart. It then updates the session with the
     * authenticated user's details and the merged cart, and changes the session ID
     * to prevent session fixation attacks.
     *
     * @param user The UserLoginDto containing the user's credentials.
     * @param request The HttpServletRequest to change the session ID.
     * @param session The HttpSession to manage user and cart attributes.
     * @return A redirect string to the homepage.
     */
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

    /**
     * Handles the user logout process.
     * This method removes the user and cart attributes from the session, effectively
     * logging the user out. It adds a flash attribute to display a success message
     * on the next page.
     *
     * @param session The HttpSession object.
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the homepage.
     */
    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes attributes){
        session.removeAttribute(USER_ATTRIBUTE_KEY);
        session.removeAttribute(CART_ATTRIBUTE_KEY);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "You have successfully signed out.");
        return "redirect:/";
    }

    /**
     * Displays the user registration form.
     *
     * @return The view name for the user creation form.
     */
    @GetMapping("/register")
    public String getRegisterForm() {
        return "user/create_user";
    }

    /**
     * Handles the user registration process.
     * This method creates a new user based on the provided data and then
     * redirects the user to the login page with a success message,
     * prompting them to log in with their new credentials.
     *
     * @param user The UserCreateDto containing the new user's details.
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the homepage.
     */
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
