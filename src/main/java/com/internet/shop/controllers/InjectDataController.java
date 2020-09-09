package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User userBob = new User("Bob");
        User userTom = new User("Tom");
        userBob.setPassword("12345");
        userTom.setPassword("12345");
        userService.create(userBob);
        userService.create(userTom);
        Product iphone = new Product("Iphone", 1000);
        Product xiaomi = new Product("Xiaomi", 600);
        Product samsung = new Product("Samsung", 800);
        Product lenovo = new Product("Lenovo", 800);
        productService.create(iphone);
        productService.create(xiaomi);
        productService.create(samsung);
        productService.create(lenovo);
        ShoppingCart cart = new ShoppingCart(USER_ID);
        shoppingCartService.create(cart);
        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
