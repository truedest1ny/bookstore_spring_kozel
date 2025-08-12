package com.kozel.bookstore.web.controller.redirect;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.kozel.bookstore.util.WebConstants.ERROR_MESSAGE_KEY;

/**
 * Controller to prevent 405 "Method Not Allowed" errors for employee order actions.
 * This class handles GET requests to endpoints designed for POST methods, such as
 * archiving and approving orders. It redirects the user back to the main orders page
 * with an informative flash message, ensuring that state-changing operations
 * are not triggered by direct URL access.
 */
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderActionRedirectController {

    /**
     * Handles GET requests to the "/orders/archive/{id}" endpoint.
     * Since archiving an order is a state-changing operation, it must be performed
     * via a POST request. This method intercepts direct GET requests and redirects
     * the user to the main orders list with a warning message.
     *
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the main orders list page.
     */
    @GetMapping("/archive/{id}")
    public String archiveOrderGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Archiving order is only available through a form or system requests!");
        return "redirect:/orders";
    }

    /**
     * Handles GET requests to the "/orders/approve/{id}" endpoint.
     * Approving an order is a state-changing operation that requires a POST request.
     * This method catches any direct GET requests and redirects the user to the
     * orders list with a warning message.
     *
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the main orders list page.
     */
    @GetMapping("/approve/{id}")
    public String approveOrderGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Approving order is only available through a form or system requests!");
        return "redirect:/orders";
    }
}
