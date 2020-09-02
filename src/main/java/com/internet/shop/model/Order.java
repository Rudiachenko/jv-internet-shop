package com.internet.shop.model;

import java.util.List;

public class Order {
    private long id;
    private long userId;
    private List<Product> products;

    public Order(List<Product> products, long userId) {
        this.products = products;
        this.userId = userId;
    }

    public void setId(long id) {
        this.id = id;
    }
}
