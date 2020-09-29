package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.UserDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UsersDaoJdbcImpl implements UserDao {
    @Override
    public User create(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO users (login, password) VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                long userId = generatedKeys.getLong(1);
                user.setId(userId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("It is impossible to create a user: "
                    + user, e);
        }
        insertRoleToUsersRolesTable(user);
        return setRoleToUser(user);
    }

    @Override
    public Optional<User> getById(Long id) {
        User user = new User();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM users WHERE user_id = ? AND deleted = FALSE;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get user with id "
                    + id + " is failed", e);
        }
        return Optional.of(setRoleToUser(user));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        User user = new User();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM users WHERE login = ? AND deleted = FALSE;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createUserFromResultSet(resultSet);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get user with login "
                    + login + " is failed", e);
        }
        return Optional.of(setRoleToUser(user));
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM users WHERE deleted = FALSE;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get users is failed", e);
        }
        for (User user : userList){
            setRoleToUser(user);
        }
        return userList;
    }

    @Override
    public User update(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "UPDATE users SET login = ?, password = ? "
                    + "WHERE user_id = ? AND deleted = FALSE;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Update of user with id "
                    + user.getId() + " is failed", e);
        }
        deleteUserRoles(user);
        insertRoleToUsersRolesTable(user);
        return setRoleToUser(user);
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "UPDATE users SET deleted = TRUE WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Delete of user with id "
                    + id + " is failed", e);
        }
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("user_id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        return new User(userId, login, password);
    }

    private User setRoleToUser(User user) {
        Set<Role> roles = new HashSet<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM roles INNER JOIN users_roles as ur "
                    + "on roles.role_id = ur.role_id WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long roleId = resultSet.getLong("role_id");
                String roleName = resultSet.getString("role_name");
                Role role = Role.of(roleName);
                role.setId(roleId);
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("It's impossible to set role "
                    + "for user with id " + user.getId(), e);
        }
        user.setRoles(roles);
        return user;
    }

    private User insertRoleToUsersRolesTable(User user) {
        Set<Role> roles = user.getRoles();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO users_roles(user_id, role_id) "
                    + "VALUES (?, (SELECT role_id FROM roles WHERE role_name = ?))";
            PreparedStatement statement = connection.prepareStatement(query);
            for (Role role : roles) {
                statement.setLong(1, user.getId());
                statement.setString(2, role.getRoleName().name());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Insert role of user with id "
                    + user.getId() + " is failed ", e);
        }
        return user;
    }

    private void deleteUserRoles(User user) {
        String query = "DELETE FROM users_roles WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products from order", e);
        }
    }
}
