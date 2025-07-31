package com.kozel.bookstore.web.controller.redirect;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.kozel.bookstore.util.WebConstants.ERROR_MESSAGE_KEY;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartActionRedirectController {

    @GetMapping("/add")
    public String addToCartGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Adding item to cart is only available through a form or system requests!");
        return "redirect:/books";
    }

    @GetMapping("/remove")
    public String removeFromCartGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Removing item from cart is only available through a form or system requests!");
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCartGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Clearing cart is only available through a form or system requests!");
        return "redirect:/books";
    }
}
