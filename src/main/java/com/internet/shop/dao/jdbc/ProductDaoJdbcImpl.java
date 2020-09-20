package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product item) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO products (name, price) "
                    + "VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                long productId = generatedKeys.getLong(1);
                item.setId(productId);
            }
            return item;
        } catch (SQLException e) {
            throw new DataProcessingException("It is impossible to create a product: "
                    + item, e);
        }
    }

    @Override
    public Optional<Product> getById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM products "
                    + "WHERE product_id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Product product = createProductFromResultSet(resultSet);
            return Optional.of(product);
        } catch (SQLException e) {
            throw new DataProcessingException("Get product with id "
                    + id + " is failed", e);
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> productsList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM products;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = createProductFromResultSet(resultSet);
                productsList.add(product);
            }
            return productsList;
        } catch (SQLException e) {
            throw new DataProcessingException("Get products is failed", e);
        }
    }

    @Override
    public Product update(Product item) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "UPDATE products "
                    + "SET name = ?, price = ? "
                    + "WHERE product_id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.setLong(3, item.getId());
            statement.execute();
            return item;
        } catch (SQLException e) {
            throw new DataProcessingException("Update of product with id "
                    + item.getId() + " is failed", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "UPDATE products "
                    + "SET deleted = 1 "
                    + "WHERE product_id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBigDecimal(1, BigDecimal.valueOf(id));
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Delete of product with id "
                    + id + " is failed", e);
        }
    }

    private Product createProductFromResultSet(ResultSet resultSet) throws SQLException {
        long productId = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        long price = resultSet.getLong("price");
        return new Product(productId, name, price);
    }
}
