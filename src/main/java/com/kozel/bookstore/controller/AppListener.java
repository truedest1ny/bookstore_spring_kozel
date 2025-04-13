package com.kozel.bookstore.controller;

import com.kozel.bookstore.AppConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@WebListener
@Slf4j
public class AppListener implements ServletContextListener {

    private static AnnotationConfigApplicationContext CONTEXT;

    public static AnnotationConfigApplicationContext getContext() {
        return CONTEXT;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        CONTEXT = new AnnotationConfigApplicationContext(AppConfig.class);
        log.info("Context initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        log.debug("ServletContextDestroy event");
        CONTEXT.close();
        log.info("Context destroyed");
    }
}
