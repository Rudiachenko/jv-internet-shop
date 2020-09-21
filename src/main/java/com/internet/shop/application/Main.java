package com.internet.shop.application;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;

public class Main {
    private static final Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductDao productDaoJdbc = (ProductDao) injector.getInstance(ProductDao.class);
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
        productDaoJdbc.delete(2L);
        Product lenovo = new Product(2L, "Lenovo", 600);
        productDaoJdbc.update(lenovo);
        System.out.println(productDaoJdbc.getAll());
    }
}
