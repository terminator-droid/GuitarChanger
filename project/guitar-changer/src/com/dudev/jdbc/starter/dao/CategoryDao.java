package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.entity.Category;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;

import javax.swing.text.html.Option;
import java.lang.module.FindException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDao implements Dao<Integer, Category> {

    private static final CategoryDao INSTANCE = new CategoryDao();

    private static final String FIND_ALL_SQL = """
            SELECT id, name FROM
            project.categories
            """;
    private static final String FIND_BY_ID = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private CategoryDao() {
    }

    public static CategoryDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Category> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                categories.add(createCategory(resultSet));
            }
            return categories;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Category createCategory(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }

    @Override
    public Optional<Category> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Category category = null;
            if (resultSet.next()) {
                category = createCategory(resultSet);
            }
            return Optional.ofNullable(category);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Category entity) {

    }

    @Override
    public Category save(Category entity) {
        return null;
    }


}
