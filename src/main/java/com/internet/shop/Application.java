package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        productService.create(new Product("Iphone", 1000));
        productService.create(new Product("Xiaomi", 600));
        productService.create(new Product("Samsung", 800));
        productService.create(new Product("Lenovo", 800));
        Product productForUpdateTest = new Product("Huawei", 600);
        productForUpdateTest.setId(3L);

        System.out.println("All products: ");
        productService.getAll().forEach(System.out::println);

        System.out.println("\nProduct by index 1:");
        System.out.println(productService.get(1L));

        System.out.println("\nAll products after deletion by index 1: ");
        productService.delete(1L);
        System.out.println(productService.getAll());

        System.out.println("\nAll products after update: ");
        productService.update(productForUpdateTest);
        System.out.println(productService.getAll());
    }
}
