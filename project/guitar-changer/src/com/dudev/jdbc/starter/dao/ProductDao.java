package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.dto.ProductDto;
import com.dudev.jdbc.starter.dto.ProductFilter;
import com.dudev.jdbc.starter.entity.*;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;

import java.sql.*;
import java.util.*;

import static com.dudev.jdbc.starter.util.ConstantUtil.CHANGE_DELTA;
import static java.util.stream.Collectors.joining;

public class ProductDao {

    private static final ProductDao INSTANCE = new ProductDao();
    private static final BrandDao brandDao = BrandDao.getInstance();
    private static final UserDao userDao = UserDao.getInstance();
    private static final ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();

    private static final String ADD_LIKED_PRODUCT = """
            INSERT INTO users_liked_products (user_id, product) VALUES 
            (?, ?)
            """;
    private static final String DELETE_LIKED_PRODUCT = """
            DELETE FROM users_liked_products WHERE user_id = ? AND product = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT  id, 
                    timestamp, 
                    user_id, 
                    price, 
                    is_closed, 
                    change_type,
                    change_value, 
                    change_wish,
                    brand,
                    model,
                    description
            FROM project.guitars 
            UNION ALL
            SELECT  id, 
                    timestamp, 
                    user_id, 
                    price, 
                    is_closed, 
                    change_type,
                    change_value, 
                    change_wish,
                    brand,
                    model,
                    description
            FROM project.pedals 
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private ProductDao() {
    }

    public void deleteLikedProduct(UUID userId, UUID productId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LIKED_PRODUCT)) {
            preparedStatement.setString(1, userId.toString());
            preparedStatement.setString(2, productId.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void insertLikedProduct(UUID userId, UUID productId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_LIKED_PRODUCT)) {
            preparedStatement.setString(1, userId.toString());
            preparedStatement.setString(2, productId.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Product> findAll(ProductFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.userId() != null) {
            whereSql.add(" user_id = ?");
            parameters.add(filter.userId());
        }
        if (filter.price() != 0) {
            int lowerBoundOfPrice = filter.price() - CHANGE_DELTA > 0 ? (int) (filter.price() - CHANGE_DELTA) : 0;
            whereSql.add(" between " + lowerBoundOfPrice + " AND " + (filter.price() + CHANGE_DELTA));
            parameters.add(filter.price());
        }
        whereSql.add(" is_closed = ? ");
        parameters.add(filter.isClosed());
        if (filter.changeType() != 0) {
            whereSql.add(" change_type = ? ");
            parameters.add(filter.changeType());
        }
        if (filter.changeValue() != 0) {
            whereSql.add(" change_value = ?");
            parameters.add(filter.changeValue());
        }
        if (filter.changeWish() != null) {
            whereSql.add(" change_wish LIKE ?");
            parameters.add("%" + filter.changeWish() + "%");
        }
        if (filter.brand() != 0) {
            whereSql.add(" brand = ?");
            parameters.add(filter.brand());
        }
        String where = whereSql.stream().collect(joining(" AND ", "WHERE", " LIMIT ? OFFSET ? ORDER BY timestamp"));
        String sql = FIND_ALL_SQL + where;

        parameters.add(filter.limit());
        parameters.add(filter.offset());
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(createProduct(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Product> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(createProduct(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Product> findById(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = null;
            if (resultSet.next()) {
                product = createProduct(resultSet);

            }
            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Product createProduct(ResultSet resultSet) throws SQLException {
        User user = userDao.findById(UUID.fromString(resultSet.getString("user_id"))).orElse(null);
        Brand brand = brandDao.findByName(resultSet.getString("brand")).orElse(null);
        ChangeType changeType = changeTypeDao.findById(resultSet.getInt("change_type")).orElse(null);
        return new Product(
                resultSet.getObject("id", UUID.class),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                user,
                resultSet.getDouble("price"),
                resultSet.getBoolean("is_closed"),
                changeType,
                resultSet.getDouble("change_value"),
                resultSet.getString("change_wish"),
                brand,
                resultSet.getString("model"),
                resultSet.getString("description")
        );
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
