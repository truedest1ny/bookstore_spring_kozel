package com.kozel.bookstore.web.controller.redirect;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.kozel.bookstore.util.WebConstants.ERROR_MESSAGE_KEY;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderActionRedirectController {


    @GetMapping("/archive/{id}")
    public String archiveOrderGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Archiving order is only available through a form or system requests!");
        return "redirect:/orders";
    }

    @GetMapping("/approve/{id}")
    public String approveOrderGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Approving order is only available through a form or system requests!");
        return "redirect:/orders";
    }
}
