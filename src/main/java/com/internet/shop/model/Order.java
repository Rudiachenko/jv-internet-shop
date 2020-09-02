package com.internet.shop.model;

import java.util.List;

public class Order {
    private Long id;
    private Long userId;
    private List<Product> products;

    public Order(List<Product> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    public void setId(long id) {
        this.id = id;
    }
}
