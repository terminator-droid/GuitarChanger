package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.entity.Product;
import com.dudev.jdbc.starter.entity.Role;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDao implements Dao<UUID, User> {


    private static final UserDao INSTANCE = new UserDao();

    private static final String FIND_ALL = """
            SELECT id, first_name, last_name, id, first_name, last_name, phone_number, password, address, role, username
            from project.users
            """;

    private static final String FIND_BY_ID = FIND_ALL + """ 
            WHERE users.id = ? :: uuid                       
            """;

    private static final String SAVE_SQL = """
            INSERT INTO project.users (first_name, last_name, phone_number, password, address, role, username)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    public UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        return new User(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("phone_number"),
                resultSet.getString("password"),
                resultSet.getString("address"),
                Role.valueOf(resultSet.getString("role")),
                resultSet.getString("username")
        );
    }

    @Override
    public void update(User user) {

    }

    @Override
    public User save(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.firstName());
            preparedStatement.setString(2, user.lastName());
            preparedStatement.setString(3, user.phoneNumber());
            preparedStatement.setString(4, user.phoneNumber());
            preparedStatement.setString(5, user.address());
            preparedStatement.setString(6, user.role().name());
            preparedStatement.setString(7, user.username());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            UUID id = null;
            while (generatedKeys.next()) {
                id = UUID.fromString(generatedKeys.getString("id"));
            }
//            user.setId(id);
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}