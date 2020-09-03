package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private Long userId;
    private List<Product> products;

    public Order(Long userId) {
        products = new ArrayList<>();
        this.userId = userId;
    }

    public void setId(long id) {
        this.id = id;
    }
}
