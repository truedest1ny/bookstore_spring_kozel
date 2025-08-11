package com.kozel.bookstore.web.controller.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.kozel.bookstore.util.WebConstants.*;

/**
 * Controller for handling servlet container errors such as 4xx and 5xx status codes.
 * This class maps specific error URIs to custom error pages, providing
 * a more user-friendly experience than default server error pages.
 */
@Controller
public class ServletErrorHandler {

    /**
     * Handles general servlet container errors (e.g., 400, 500).
     * It retrieves the error status code and message from the request attributes
     * and adds them to the model for display on the error page.
     *
     * @param request The HttpServletRequest object containing error attributes.
     * @param model The Spring Model to pass data to the view.
     * @return The view name for the general servlet error page.
     */
    @RequestMapping("/error")
    public String handleError (HttpServletRequest request, Model model){
        model.addAttribute(ERROR_STATUS_KEY, request.getAttribute("jakarta.servlet.error.status_code"))
                .addAttribute(ERROR_REASON_KEY, request.getAttribute("jakarta.servlet.error.message"));
            return "error/servlet_error";
    }

    /**
     * Handles 403 Forbidden errors.
     * This method redirects users to a dedicated forbidden page when they
     * attempt to access a resource without sufficient permissions.
     *
     * @return The view name for the forbidden error page.
     */
    @RequestMapping("/forbidden")
    public String handleForbiddenError(){
        return "error/forbidden";
    }

    /**
     * Handles 404 Not Found errors.
     * It retrieves the original request URI to inform the user which URL
     * was not found. The URL is added to the model for display.
     *
     * @param request The HttpServletRequest object.
     * @param model The Spring Model to pass the URL to the view.
     * @return The view name for the not found error page.
     */
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
