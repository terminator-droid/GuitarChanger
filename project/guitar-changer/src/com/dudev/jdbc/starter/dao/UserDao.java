package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.entity.Role;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<User> findAll() {
        return null;
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
    public void update(User entity) {

    }

    @Override
    public User save(User entity) {
        return null;
    }
}
