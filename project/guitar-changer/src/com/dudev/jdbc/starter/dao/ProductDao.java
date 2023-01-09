package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.dto.GuitarFilter;
import com.dudev.jdbc.starter.dto.PedalFilter;
import com.dudev.jdbc.starter.dto.ProductDto;
import com.dudev.jdbc.starter.dto.ProductFilter;
import com.dudev.jdbc.starter.entity.*;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.dudev.jdbc.starter.util.ConstantUtil.CHANGE_DELTA;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ProductDao {

    private static final ProductDao INSTANCE = new ProductDao();
    private static final BrandDao brandDao = BrandDao.getInstance();
    private static final UserDao userDao = UserDao.getInstance();
    private static final ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();
    private static final GuitarDao guitarDao = GuitarDao.getInstance();
    private static final PedalDao pedalDao = PedalDao.getInstance();

    private static final String DELETE_PRODUCTS = """
            DELETE FROM products
            """;
    private static final String ADD_LIKED_PRODUCT = """
            INSERT INTO users_liked_products (user_id, product) VALUES 
            (?, ?)
            """;
    private static final String DELETE_LIKED_PRODUCT = """
            DELETE FROM users_liked_products WHERE user_id = ? AND product = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT * FROM (SELECT  id, 
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
            FROM project.pedals) as prod 
           
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ? :: uuid
            """;

    private ProductDao() {
    }

    @SneakyThrows
    public void deleteById(UUID id) {
        String sql = DELETE_PRODUCTS + "WHERE ID = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public void deleteById(UUID id, Connection connection) {
        guitarDao.delete(GuitarFilter.builder().id(id).build(), connection);
        pedalDao.delete(PedalFilter.builder().id(id).build(), connection);
    }

    public void closeProduct(UUID id, Connection connection) {
        guitarDao.closeGuitar(id, connection);
        pedalDao.closePedal(id, connection);
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
            whereSql.add(" prod.user_id = ?");
            parameters.add(filter.userId());
        }
        if (filter.price() != 0) {
            double lowerBoundOfPrice = filter.price() - CHANGE_DELTA > 0 ? (double) (filter.price() - CHANGE_DELTA) : 0;
            whereSql.add(" prod.price between " + lowerBoundOfPrice + " AND " + (filter.price() + CHANGE_DELTA));
        }
        if (!filter.isClosed()) {
            whereSql.add(" prod.is_closed = ? ");
            parameters.add(false);
        }
        if (filter.changeType() != 0) {
            whereSql.add(" prod.change_type = ? ");
            parameters.add(filter.changeType());
        }
        if (filter.changeValue() != 0) {
            double lowerBoundOfPrice = filter.changeValue() - CHANGE_DELTA > 0 ? (double) (filter.changeValue() - CHANGE_DELTA) : 0;
            whereSql.add(" prod.change_value between " + lowerBoundOfPrice + " AND " + (filter.changeValue() + CHANGE_DELTA));
//
//            whereSql.add(" prod.change_value = ?");
//            parameters.add(filter.changeValue());
        }
        if (filter.changeWish() != null) {
            whereSql.add(" prod.change_wish LIKE ?");
            parameters.add("%" + filter.changeWish() + "%");
        }
        if (filter.brand() != 0) {
            whereSql.add(" prod.brand = ?");
            parameters.add(filter.brand());
        }
        String where = whereSql.stream().collect(joining(" AND ", "WHERE", " ORDER BY timestamp LIMIT ? OFFSET ? "));
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
        String where = " where is_closed = false";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL + where)) {
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
        return Product.builder()
                .id(UUID.fromString(resultSet.getString("id")))
                .brand(brand)
                .changeType(changeType)
                .changeValue(resultSet.getDouble("change_value"))
                .model(resultSet.getString("model"))
                .description(resultSet.getString("description"))
                .changeWish(resultSet.getString("change_wish"))
                .isClosed(resultSet.getBoolean("is_closed"))
                .timestamp(resultSet.getTimestamp("timestamp").toLocalDateTime())
                .user(user)
                .model(resultSet.getString("model"))
                .price(resultSet.getDouble("price"))
                .build();
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
