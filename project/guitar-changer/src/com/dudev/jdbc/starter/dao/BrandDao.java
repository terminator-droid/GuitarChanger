package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.entity.Brand;
import com.dudev.jdbc.starter.entity.Category;
import com.dudev.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BrandDao implements Dao<Integer, Brand> {

    private static final BrandDao INSTANCE = new BrandDao();
    private static final CategoryDao categoryDao = CategoryDao.getInstance();
    private static final String FIND_ALL_SQL = """
            SELECT id, name, category FROM
            project.brands
            """;
    private static final String FIND_BY_ID = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    public static final String FIND_BY_NAME = FIND_ALL_SQL +
            """
            WHERE name = ?
            """;
    private BrandDao() {
    }

    public static BrandDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Brand> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Brand> brands = new ArrayList<>();
            while (resultSet.next()) {
                brands.add(createBrand(resultSet));
            }
            return brands;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Brand> findByName(String name) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            Brand brand = null;
            if (resultSet.next()) {
                brand = createBrand(resultSet);
            }
            return Optional.ofNullable(brand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Brand> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Brand brand = null;
            if (resultSet.next()) {
                brand = createBrand(resultSet);
            }
            return Optional.ofNullable(brand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Brand createBrand(ResultSet resultSet) throws SQLException {
        Category category = categoryDao.findById(resultSet.getInt("category")).orElse(null);
        return new Brand(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                category
        );
    }


    @Override
    public void update(Brand entity) {

    }

    @Override
    public Brand save(Brand entity) {
        return null;
    }
}
