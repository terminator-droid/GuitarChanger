package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.dto.UserFilter;
import com.dudev.jdbc.starter.entity.Product;
import com.dudev.jdbc.starter.entity.Role;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;
import lombok.SneakyThrows;
import org.postgresql.jdbc2.ArrayAssistant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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
    private static final String DELETE_SQL = """
            DELETE FROM project.users 
            WHERE id = ? :: uuid
            """;
    private static final String UPDATE_PASSWORD = """
            UPDATE project.users SET password = ?
            WHERE id = ? :: uuid
            """;

    public UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }


    public List<User> findAll(UserFilter filter) {
        List<String> whereConditions = new ArrayList<>();
        List<Object> fields = new ArrayList<>();
        if (filter.phoneNumber() != null) {
            whereConditions.add(" phone_number = ? ");
            fields.add(filter.phoneNumber());
        }
        if (filter.password() != null) {
            whereConditions.add(" password = ?");
            fields.add(filter.password());
        }
        if (filter.username() != null) {
            whereConditions.add(" username = ?");
            fields.add(filter.username());
        }
        String whereSql = String.join(" AND ", whereConditions);
        String sql = FIND_ALL + "WHERE " + whereSql;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < fields.size(); i++) {
                preparedStatement.setObject(i + 1, fields.get(i));
            }
            List<User> users = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
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
        return User.builder()
                .id(UUID.fromString(resultSet.getString("id")))
                .phoneNumber(resultSet.getString("phone_number"))
                .password(resultSet.getString("password"))
                .role(Role.find(resultSet.getString("role")).get())
                .lastName(resultSet.getString("last_name"))
                .firstName(resultSet.getString("first_name"))
                .address(resultSet.getString("address"))
                .username(resultSet.getString("username"))
                .build();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public User save(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getRole().name());
            preparedStatement.setString(7, user.getUsername());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            UUID id = null;
            while (generatedKeys.next()) {
                id = UUID.fromString(generatedKeys.getString("id"));
            }
            user.setId(id);
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @SneakyThrows
    public User delete(UUID id, Connection connection) {
        PreparedStatement preparedStatement = null;
        User deletedUser = findById(id).orElse(null);
        try {
            preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return deletedUser;
    }

    @SneakyThrows
    public void updatePassword(String userId, String password) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, userId);
            preparedStatement.executeUpdate();
        }

    }
}