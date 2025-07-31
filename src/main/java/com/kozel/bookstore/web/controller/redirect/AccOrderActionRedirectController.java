package com.kozel.bookstore.web.controller.redirect;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.kozel.bookstore.util.WebConstants.ERROR_MESSAGE_KEY;

@Controller
@RequestMapping("/ordered")
@RequiredArgsConstructor
public class AccOrderActionRedirectController {


    @GetMapping("/add")
    public String addOrderGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Adding an order is only available through a form or system requests!");
        return "redirect:/ordered";
    }

    @GetMapping("/cancel/{id}")
    public String cancelOrderGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Cancelling order is only available through a form or system requests!");
        return "redirect:/ordered";
    }
}
