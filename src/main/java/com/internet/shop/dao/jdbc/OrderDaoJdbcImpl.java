package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO orders (user_id) VALUES (?);";
            PreparedStatement statement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                long orderId = generatedKeys.getLong(1);
                order.setId(orderId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("It is impossible to create a order for user id: "
                    + order.getUserId(), e);
        }
        insertDataToOrdersProducts(order);
        return order;
    }

    @Override
    public Optional<Order> getById(Long id) {
        Order order = new Order();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM orders WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                order = createOrderFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get order with id "
                    + id + " is failed", e);
        }
        order.setProducts(extractProductsForOrder(order.getId()));
        return Optional.of(order);
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM orders";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = createOrderFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get orders failed", e);
        }
        for (Order order : orders) {
            order.setProducts(extractProductsForOrder(order.getId()));
        }
        return orders;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM orders WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = createOrderFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get order of user with id "
                    + userId + " is failed", e);
        }
        for (Order order : orders) {
            order.setProducts(extractProductsForOrder(order.getId()));
        }
        return orders;
    }

    @Override
    public Order update(Order order) {
        deleteProductsFromOrder(order.getId());
        insertDataToOrdersProducts(order);
        return order;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "UPDATE orders SET deleted = TRUE WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            deleteProductsFromOrder(id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Delete of order with id "
                    + id + " is failed", e);
        }
    }

    private void insertDataToOrdersProducts(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO orders_products (product_id, order_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : order.getProducts()) {
                statement.setLong(1, product.getId());
                statement.setLong(2, order.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert order with id "
                    + order.getId() + " is failed", e);
        }
    }

    private List<Product> extractProductsForOrder(long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM products "
                    + "INNER JOIN orders_products as op on products.product_id = op.product_id "
                    + "WHERE order_id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                long price = resultSet.getLong("price");
                Product product = new Product(productId, name, price);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Get order with id : "
                    + id + " is failed", e);
        }
    }

    private void deleteProductsFromOrder(Long orderId) {
        String query = "DELETE FROM orders_products WHERE order_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products from order", e);
        }
    }

    private Order createOrderFromResultSet(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("user_id");
        long orderId = resultSet.getLong("order_id");
        return new Order(orderId, userId);
    }
}
