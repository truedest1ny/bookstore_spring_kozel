package com.kozel.bookstore.web.controller.redirect;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.kozel.bookstore.util.WebConstants.ERROR_MESSAGE_KEY;

/**
 * Controller to prevent 405 "Method Not Allowed" errors for specific endpoints.
 * This class handles GET requests to URLs that are intended exclusively for POST methods,
 * redirecting the user back to a safe page with an informative error message.
 * This prevents users from manually typing incorrect URLs and breaking the application's flow.
 */
@Controller
@RequestMapping("/ordered")
@RequiredArgsConstructor
public class AccOrderActionRedirectController {

    /**
     * Handles GET requests to the "/ordered/add" endpoint.
     * Since adding an order should only be done via a POST request (e.g., from a form),
     * this method intercepts direct GET requests and redirects the user to the main
     * orders page with an error message.
     *
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the main orders page.
     */
    @GetMapping("/add")
    public String addOrderGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Adding an order is only available through a form or system requests!");
        return "redirect:/ordered";
    }

    /**
     * Handles GET requests to the "/ordered/cancel/{id}" endpoint.
     * Order cancellation is a state-changing operation and should only be performed
     * via a POST request. This method catches any direct GET requests and redirects
     * the user to the orders page with a warning.
     *
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the main orders page.
     */
    @GetMapping("/cancel/{id}")
    public String cancelOrderGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Cancelling order is only available through a form or system requests!");
        return "redirect:/ordered";
    }
}
