package com.kozel.bookstore.web.controller.redirect;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.kozel.bookstore.util.WebConstants.ERROR_MESSAGE_KEY;
/**
 * Controller to prevent 405 "Method Not Allowed" errors for user management actions.
 * This class handles GET requests to endpoints designed for POST methods, such as
 * deleting a user. It redirects the user back to the main users page with
 * an informative flash message, ensuring that state-changing operations
 * are not triggered by direct URL access.
 */
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserActionRedirectController {

    /**
     * Handles GET requests to the "/users/delete/{id}" endpoint.
     * Since deleting a user is a state-changing operation, it must be performed
     * via a POST request. This method intercepts direct GET requests and redirects
     * the user to the main users list with a warning message.
     *
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the main users list page.
     */
    @GetMapping("/delete/{id}")
    public String deleteUserGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Deleting user is only available through a form or system requests!");
        return "redirect:/users";
    }
}
