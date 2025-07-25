package com.kozel.bookstore.web.controller.redirect;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.kozel.bookstore.util.WebConstants.ERROR_MESSAGE_KEY;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookActionRedirectController {


    @GetMapping("/delete/{id}")
    public String deleteBookGet(RedirectAttributes attributes) {
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY,
                "Deleting book is only available through a form or system requests!");
        return "redirect:/books";
    }
}
