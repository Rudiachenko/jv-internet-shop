package com.internet.shop.dao.impl;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long productId) {
        return Storage.products.stream()
                .filter(x -> x.getId() == productId)
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public Product update(Product product) {
        List<Product> products = Storage.products;
        for (int i = 0; i < products.size(); i++) {
            if (product.getId() == products.get(i).getId()) {
                return products.set(i, product);
            }
        }
        return product;
    }

    @Override
    public boolean delete(Long productId) {
        return Storage.products.removeIf(product -> product.getId() == productId);
    }
}
