package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.entity.ChangeType;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChangeTypeDao implements Dao<Integer, ChangeType> {

    private static final ChangeTypeDao INSTANCE = new ChangeTypeDao();

    private ChangeTypeDao() {
    }

    public static ChangeTypeDao getInstance() {
        return INSTANCE;
    }

    public static final String FIND_ALL = """
            SELECT change_type, description FROM project.change_types
            """;
    public static final String FIND_BY_ID = """
            SELECT change_type, description
            from project.change_types 
            WHERE change_type = ?
            """;
    public static final String FIND_BY_NAME = """
            SELECT change_type, description
            from project.change_types 
            WHERE description = ?
            """;

    @SneakyThrows
    @Override
    public List<ChangeType> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<ChangeType> changeTypes = new ArrayList<>();
            while (resultSet.next()) {
                ChangeType changeType = ChangeType.builder()
                        .changeType(resultSet.getInt("change_type"))
                        .description(resultSet.getString("description"))
                        .build();
                changeTypes.add(changeType);
            }
            return changeTypes;
        }
    }

    public Optional<ChangeType> findByName(String name) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            ChangeType changeType = null;
            if (resultSet.next()) {
                changeType = ChangeType.builder()
                        .changeType(resultSet.getInt("change_type"))
                        .description(resultSet.getString("description"))
                        .build();
            }
            return Optional.ofNullable(changeType);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public Optional<ChangeType> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            ChangeType changeType = null;
            if (resultSet.next()) {
                changeType = ChangeType.builder()
                        .changeType(resultSet.getInt("change_type"))
                        .description(resultSet.getString("description"))
                        .build();
            }
            return Optional.ofNullable(changeType);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(ChangeType entity) {

    }

    @Override
    public ChangeType save(ChangeType entity) {
        return null;
    }
}
