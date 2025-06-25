package com.kozel.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/error")
    public String handleError (HttpServletRequest request, Model model){
        model.addAttribute("status", request.getAttribute("jakarta.servlet.error.status_code"))
                .addAttribute("reason", request.getAttribute("jakarta.servlet.error.message"));
            return "error/servlet_error";
    }

    @RequestMapping("/not_found")
    public String handleNotFoundException(HttpServletRequest request, Model model){
        model.addAttribute("url", request.getAttribute("jakarta.servlet.error.request_uri"));
        return "error/not_found";
    }


}
