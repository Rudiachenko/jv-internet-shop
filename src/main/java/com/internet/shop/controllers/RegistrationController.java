package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/user/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("psw");
        String passwordRepeat = req.getParameter("psw-rpt");
        if (password.length() < 6) {
            req.setAttribute("message", "Your password length should be at least 6 characters.");
            req.getRequestDispatcher("/WEB-INF/views/user/registration.jsp").forward(req, resp);
        }
        if (password.equals(passwordRepeat)) {
            resp.sendRedirect(req.getContextPath() + "/users/all");
            User newUser = new User(login);
            newUser.setPassword(password);
            userService.create(newUser);
            System.out.println(userService.getAll());
        } else {
            req.setAttribute("message", "Your password and repeat password aren't the same.");
            req.getRequestDispatcher("/WEB-INF/views/user/registration.jsp").forward(req, resp);
        }
    }
}
