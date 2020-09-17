package com.internet.shop.controllers.user;

import com.internet.shop.exceptions.IncorrectRegistrationDataException;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.RegisterService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final RegisterService registerService
            = (RegisterService) injector.getInstance(RegisterService.class);

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
        try {
            User newUser = registerService.register(login, password, passwordRepeat);
            newUser.setRoles(Set.of(Role.of("USER")));
            userService.create(newUser);
            ShoppingCart cart = new ShoppingCart(newUser.getId());
            shoppingCartService.create(cart);
        } catch (IncorrectRegistrationDataException e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/user/registration.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
