package com.kozel.bookstore.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ServletErrorHandler {

    @RequestMapping("/error")
    public String handleError (HttpServletRequest request, Model model){
        model.addAttribute("status", request.getAttribute("jakarta.servlet.error.status_code"))
                .addAttribute("reason", request.getAttribute("jakarta.servlet.error.message"));
            return "error/servlet_error";
    }

    @RequestMapping("/forbidden")
    public String handleForbiddenError(){
        return "error/forbidden";
    }

    @RequestMapping("/not_found")
    public String handleNotFoundError(HttpServletRequest request, Model model){
        String url = (String) request.getAttribute("authFilterUrl");

        if (url == null) {
            url = (String) request.getAttribute("jakarta.servlet.error.request_uri");
        }

        model.addAttribute("url", url);
        return "error/not_found";
    }

}
