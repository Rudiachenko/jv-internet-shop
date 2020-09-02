package com.internet.shop.model;

import java.util.List;

public class ShoppingCart {
    private long id;
    private long userId;
    private List<Product> products;

    public ShoppingCart(List<Product> products, long userId) {
        this.products = products;
        this.userId = userId;
    }

    public void setId(long id) {
        this.id = id;
    }
}
