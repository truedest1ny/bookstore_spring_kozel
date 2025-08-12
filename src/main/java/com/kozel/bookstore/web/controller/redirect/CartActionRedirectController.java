package com.kozel.bookstore.web.controller.redirect;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.kozel.bookstore.util.WebConstants.ERROR_MESSAGE_KEY;

/**
 * Controller to prevent 405 "Method Not Allowed" errors for shopping cart actions.
 * This class handles GET requests to endpoints designed for POST methods
 * (e.g., adding, removing, or clearing items) and redirects the user
 * to an appropriate page with a flash message explaining the issue.
 * This ensures that state-changing operations are not triggered by direct URL access.
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartActionRedirectController {

    /**
     * Handles GET requests to the "/cart/add" endpoint.
     * Since adding an item to the cart is a state-changing operation, it must
     * be done via a POST request. This method intercepts direct GET requests
     * and redirects the user to the books page with a warning.
     *
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the books list page.
     */
    @GetMapping("/add")
    public String addToCartGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Adding item to cart is only available through a form or system requests!");
        return "redirect:/books";
    }

    /**
     * Handles GET requests to the "/cart/remove" endpoint.
     * Removing items from the cart should be handled by a POST request.
     * This method catches direct GET requests and redirects the user to the
     * cart page with a warning message.
     *
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the shopping cart page.
     */
    @GetMapping("/remove")
    public String removeFromCartGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Removing item from cart is only available through a form or system requests!");
        return "redirect:/cart";
    }

    /**
     * Handles GET requests to the "/cart/clear" endpoint.
     * Clearing the entire cart is a state-changing operation that requires a POST request.
     * This method intercepts direct GET access and redirects the user to the
     * books page with a warning.
     *
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the books list page.
     */
    @GetMapping("/clear")
    public String clearCartGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Clearing cart is only available through a form or system requests!");
        return "redirect:/books";
    }
}
