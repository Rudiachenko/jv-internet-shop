package com.internet.shop.application;

import com.internet.shop.dao.jdbc.ProductDaoJdbcImpl;
import com.internet.shop.model.Product;

public class Main {
    public static void main(String[] args) {
        ProductDaoJdbcImpl productDaoJdbc = new ProductDaoJdbcImpl();
        Product iphone = new Product("Iphone", 700);
        Product xiaomi = new Product("Xiaomi", 500);
        Product samsung = new Product("Samsung", 600);
        productDaoJdbc.create(iphone);
        productDaoJdbc.create(xiaomi);
        productDaoJdbc.create(samsung);
        System.out.println(productDaoJdbc.getById(3L).get());
        System.out.println(productDaoJdbc.getAll());
        Product huawei = new Product(3L, "Huawei", 600);
        productDaoJdbc.update(huawei);
        System.out.println(productDaoJdbc.getAll());
        productDaoJdbc.delete(2L);
    }
}
