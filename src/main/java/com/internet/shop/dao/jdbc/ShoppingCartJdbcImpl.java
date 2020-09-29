package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ShoppingCartJdbcImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO shopping_carts (user_id) VALUES (?);";
            PreparedStatement statement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, shoppingCart.getUserId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                long shoppingCartId = generatedKeys.getLong(1);
                shoppingCart.setId(shoppingCartId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("It is impossible to create "
                    + "a shopping cart for user with id: "
                    + shoppingCart.getUserId(), e);
        }
        insertDataToShoppingCartsProducts(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> getById(Long id) {
        ShoppingCart shoppingCart;
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM shopping_carts WHERE cart_id = ? AND deleted = FALSE";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                shoppingCart = createShoppingCartFromResultSet(resultSet);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get shopping cart with id "
                    + id + " is failed", e);
        }
        shoppingCart.setProducts(extractProductsForShoppingCart(shoppingCart.getId()));
        return Optional.of(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        ShoppingCart shoppingCart;
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM shopping_carts WHERE user_id = ? AND deleted = FALSE";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                shoppingCart = createShoppingCartFromResultSet(resultSet);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get shopping cart of user with id "
                    + userId + " is failed", e);
        }
        shoppingCart.setProducts(extractProductsForShoppingCart(shoppingCart.getUserId()));
        return Optional.of(shoppingCart);
    }

    @Override
    public List<ShoppingCart> getAll() {
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM shopping_carts WHERE deleted = FALSE";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ShoppingCart shoppingCart = createShoppingCartFromResultSet(resultSet);
                shoppingCarts.add(shoppingCart);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get shopping carts failed", e);
        }
        for (ShoppingCart shoppingCart : shoppingCarts) {
            shoppingCart.setProducts(extractProductsForShoppingCart(shoppingCart.getId()));
        }
        return shoppingCarts;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        deleteProductsFromShoppingCart(shoppingCart.getId());
        insertDataToShoppingCartsProducts(shoppingCart);
        return shoppingCart;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "UPDATE shopping_carts SET deleted = TRUE WHERE cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Delete of shopping cart with id "
                    + id + " is failed", e);
        }
    }

    private void insertDataToShoppingCartsProducts(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO shopping_carts_products "
                    + "(cart_id, product_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : shoppingCart.getProducts()) {
                statement.setLong(1, shoppingCart.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert shopping cart with id "
                    + shoppingCart.getId(), e);
        }
    }

    private List<Product> extractProductsForShoppingCart(long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM products "
                    + "INNER JOIN shopping_carts_products as scp "
                    + "on products.product_id = scp.product_id WHERE cart_id = ?;";
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
            throw new DataProcessingException("Can't extract products for shopping cart", e);
        }
    }

    private ShoppingCart createShoppingCartFromResultSet(ResultSet resultSet) throws SQLException {
        long cartId = resultSet.getLong("cart_id");
        long userId = resultSet.getLong("user_id");
        return new ShoppingCart(cartId, userId);
    }

    private void deleteProductsFromShoppingCart(Long cartId) {
        String query = "DELETE FROM shopping_carts_products WHERE cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products from shopping cart", e);
        }
    }
}
