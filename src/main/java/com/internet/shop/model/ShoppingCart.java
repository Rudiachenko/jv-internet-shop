package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private Long userId;
    private List<Product> products;

    public ShoppingCart(Long userId) {
        products = new ArrayList<>();
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }
}
