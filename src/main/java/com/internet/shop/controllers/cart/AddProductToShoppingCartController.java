package com.internet.shop.controllers.cart;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductToShoppingCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Product product = productService.get(id);
        List<Product> products = shoppingCartService.getByUserId(USER_ID).getProducts();
        if (products.contains(product)) {
            List<Product> allProducts = productService.getAll();
            req.setAttribute("products", allProducts);
            req.setAttribute("message", "This item is already exist in shopping cart.");
            req.getRequestDispatcher("/WEB-INF/views/product/all.jsp").forward(req, resp);
        } else {
            shoppingCartService.addProduct(shoppingCartService.getByUserId(USER_ID), product);
            System.out.println(shoppingCartService.getByUserId(USER_ID));
            resp.sendRedirect(req.getContextPath() + "/products/all");
        }
    }
}
