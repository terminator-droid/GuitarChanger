package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.entity.ChangeType;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ChangeTypeDao implements Dao<Integer, ChangeType> {

    private static final ChangeTypeDao INSTANCE = new ChangeTypeDao();

    private ChangeTypeDao() {
    }

    public static ChangeTypeDao getInstance() {
        return INSTANCE;
    }

    public static final String FIND_BY_ID = """
            SELECT change_type, description
            from project.change_types 
            WHERE change_type = ?
            """;

    @Override
    public List<ChangeType> findAll() {
        return null;
    }

    @Override
    public Optional<ChangeType> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            ChangeType changeType = null;
            if (resultSet.next()) {
                changeType = new ChangeType(resultSet.getInt("change_type"), resultSet.getString("description"));
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
