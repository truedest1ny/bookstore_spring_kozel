package com.kozel.bookstore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/controller")
public class FrontController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestProcess(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestProcess(req, resp);
    }

    private static void requestProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandParameter = req.getParameter("command");

        try {
            Command command = AppListener.getContext().getBean(commandParameter, Command.class);
            CommandResult result = command.process(req);
            req.getRequestDispatcher(result.getPage()).forward(req, resp);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            String id = req.getParameter("id");

            req.setAttribute("id", id);
            req.setAttribute("command", commandParameter);

            Command command = AppListener.getContext().getBean("error", Command.class);
            CommandResult result = command.process(req, e);

            resp.setStatus(result.getStatusCode());
            req.getRequestDispatcher(result.getPage()).forward(req, resp);
        }
    }

}
