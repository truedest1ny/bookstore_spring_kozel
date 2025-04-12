package com.kozel.bookstore.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@WebListener
@Slf4j
public class AppListener implements ServletContextListener {

    private static ClassPathXmlApplicationContext CONTEXT;

    public static ClassPathXmlApplicationContext getContext() {
        return CONTEXT;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        CONTEXT = new ClassPathXmlApplicationContext("config.xml");
        log.info("Context initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        log.debug("ServletContextDestroy event");
        CONTEXT.close();
        log.info("Context destroyed");
    }
}
