package com.internet.shop;

import com.internet.shop.db.Storage;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");
    private static ProductService productService;
    private static OrderService orderService;
    private static UserService userService;
    private static ShoppingCartService shoppingCartService;
    private static final Long INDEX = 1L;
    private static final Long INDEX_TWO = 2L;

    public static void main(String[] args) {
        productService = (ProductService) injector.getInstance(ProductService.class);

        shoppingCartService = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        orderService = (OrderService) injector.getInstance(OrderService.class);
        userService = (UserService) injector.getInstance(UserService.class);

        Product iphone = new Product("Iphone", 1000);
        Product xiaomi = new Product("Xiaomi", 600);
        Product samsung = new Product("Samsung", 800);
        Product lenovo = new Product("Lenovo", 800);
        productService.create(iphone);
        productService.create(xiaomi);
        productService.create(samsung);
        productService.create(lenovo);
        Product productForUpdateTest = new Product("Huawei", 600);
        productForUpdateTest.setId(lenovo.getId());

        System.out.println("All products: ");
        productService.getAll().forEach(System.out::println);
        System.out.println("\nProduct by index 1:");
        System.out.println(productService.get(INDEX));
        System.out.println("\nAll products after deletion by index 1: ");
        productService.delete(INDEX);
        System.out.println(productService.getAll());
        System.out.println("\nAll products after update: ");
        productService.update(productForUpdateTest);
        System.out.println(productService.getAll());

        User userBob = new User("Bob", "Bobby", "qwerty12456");
        User userTom = new User("Tom", "Tommy", "tyuio12345");
        userService.create(userBob);
        userService.create(userTom);
        System.out.println("\nGet user by id" + "\n" + userService.get(userBob.getId()));
        User userForUpdate = new User("Alice", "Alice", "asdfg12345");
        userForUpdate.setId(INDEX_TWO);
        System.out.println("\nUsers before update" + "\n" + userService.getAll());
        userService.update(userForUpdate);
        System.out.println("\nUsers after update" + "\n" + userService.getAll());
        userService.delete(INDEX_TWO);
        System.out.println("\nUsers after delete user Alice" + "\n" + userService.getAll());

        ShoppingCart cart = new ShoppingCart(userBob.getId());
        shoppingCartService.create(cart);
        shoppingCartService.addProduct(cart, iphone);
        shoppingCartService.addProduct(cart, lenovo);
        System.out.println("\nShopping cart before delete product" + "\n" + Storage.shoppingCarts);
        shoppingCartService.deleteProduct(cart, lenovo);
        System.out.println("\nShopping cart after delete product" + "\n" + Storage.shoppingCarts);
        System.out.println("\nGet shopping cart by user id");
        System.out.println(shoppingCartService.getByUserId(userBob.getId()));

        Order order = new Order(INDEX);
        orderService.completeOrder(cart);
        System.out.println("\nGet order of user Bob" + "\n"
                + orderService.getUserOrders(userBob.getId()));
    }
}
