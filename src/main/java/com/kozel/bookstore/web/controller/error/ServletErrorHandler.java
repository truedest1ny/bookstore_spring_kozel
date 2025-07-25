package com.kozel.bookstore.web.controller.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.kozel.bookstore.util.WebConstants.*;

@Controller
public class ServletErrorHandler {

    @RequestMapping("/error")
    public String handleError (HttpServletRequest request, Model model){
        model.addAttribute(ERROR_STATUS_KEY, request.getAttribute("jakarta.servlet.error.status_code"))
                .addAttribute(ERROR_REASON_KEY, request.getAttribute("jakarta.servlet.error.message"));
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

        model.addAttribute(URL_KEY, url);
        return "error/not_found";
    }

}
