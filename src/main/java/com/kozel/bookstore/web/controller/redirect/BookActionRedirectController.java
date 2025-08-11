package com.kozel.bookstore.web.controller.redirect;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.kozel.bookstore.util.WebConstants.ERROR_MESSAGE_KEY;

/**
 * Controller to prevent 405 "Method Not Allowed" errors for specific book management endpoints.
 * This class handles GET requests to URLs that are intended for POST methods,
 * such as deletion, and redirects the user back to the main books page with
 * an informative error message.
 */
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookActionRedirectController {

    /**
     * Handles GET requests to the "/books/delete/{id}" endpoint.
     * Since deleting a book is a state-changing operation, it should only be
     * performed via a POST request. This method intercepts direct GET requests
     * and redirects the user to the main books page with a warning message.
     *
     * @param attributes RedirectAttributes to add a flash message for the user.
     * @return A redirect string to the main books list page.
     */
    @GetMapping("/delete/{id}")
    public String deleteBookGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Deleting book is only available through a form or system requests!");
        return "redirect:/books";
    }
}
