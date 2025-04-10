package com.kozel.bookstore.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;


@WebListener
@Slf4j
public class AppListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        CommandFactory instance = CommandFactory.INSTANCE;
        log.info("Context initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        log.debug("ServletContextDestroy event");
        CommandFactory.INSTANCE.close();
        log.info("Context destroyed");
    }
}
