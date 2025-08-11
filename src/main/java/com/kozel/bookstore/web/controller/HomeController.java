package com.kozel.bookstore.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling requests to the application's homepage.
 * This class serves as the entry point for users accessing the root URL of the website.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    /**
     * Handles GET requests to the root URL ("/").
     * This method simply returns the name of the view for the main page,
     * serving as the primary landing page for the application.
     *
     * @return The view name for the homepage.
     */
    @GetMapping
    public String handleMainPage(){
        return "index";
    }
}
