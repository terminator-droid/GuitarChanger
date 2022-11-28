package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.dto.ProductFilter;
import com.dudev.jdbc.starter.entity.ChangeType;
import com.dudev.jdbc.starter.entity.Product;
import com.dudev.jdbc.starter.entity.Role;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;

import java.sql.*;
import java.util.*;

import static com.dudev.jdbc.starter.util.ConstantUtil.CHANGE_DELTA;
import static java.util.stream.Collectors.joining;

public class ProductDao implements Dao<UUID, Product> {

    private static final ProductDao INSTANCE = new ProductDao();
    private static final String DELETE_SQL = """
            DELETE FROM products
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO products (timestamp, user_id, price, change_type, change_value, change_wish) VALUES
            (?, ?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE products
            set timestamp = ?,
                user_id = ?,
                price = ?,
                change_type = ?,
                change_value = ?,
                change_wish = ?
            WHERE id = ?          
            """;
    public static final String FIND_ALL_SQL = """
            SELECT  products.id, 
                    products.timestamp, 
                    products.user_id, 
                    products.price, 
                    products.is_closed, 
                    products.change_type,
                    products.change_value, 
                    products.change_wish,
                    u.address,
                    u.first_name,
                    u.last_name,
                    u.password,
                    u.phone_number,
                    u.role,
                    ct.description
            FROM project.products 
            JOIN project.users u 
                on products.user_id = u.id
            JOIN project.change_types ct on ct.change_type = products.change_type
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;


    private ProductDao() {
    }

    public List<Product> findByPriceAndChangeType(double price, ChangeType changeType, int priceDirection) {
        char compareSign = switch (priceDirection) {
            case 1 -> '>';
            case 2 -> '<';
            default -> '=';
        };
        String whereSql = """
                 WHERE products.change_type = ? 
                 AND products.price %s ?
                 AND
                """.formatted(compareSign);
        String sql = FIND_ALL_SQL + whereSql;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, changeType.changeType());
            preparedStatement.setDouble(2, price);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(createProduct(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public void update(Product product) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(product.getTimestamp()));
            preparedStatement.setString(2, product.getUser().id().toString());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getChangeType().changeType());
            preparedStatement.setObject(5, product.getChangeValue(), Types.DOUBLE);
            preparedStatement.setObject(6, product.getChangeWish(), Types.VARCHAR);
            preparedStatement.setString(7, product.getId().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Product save(Product product) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setTimestamp(1, Timestamp.valueOf(product.getTimestamp()));
            insertStatement.setString(2, product.getUser().id().toString());
            insertStatement.setDouble(3, product.getPrice());
            insertStatement.setInt(4, product.getChangeType().changeType());
            insertStatement.setObject(5, product.getChangeValue(), Types.DOUBLE);
            insertStatement.setObject(6, product.getChangeWish(), Types.VARCHAR);

            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getObject("id", UUID.class));
            }
            return product;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Product createProduct(ResultSet resultSet) throws SQLException {
        User user = new User(
                UUID.fromString(resultSet.getString("user_id")),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("phone_number"),
                resultSet.getString("password"),
                resultSet.getString("address"),
                Role.getRoleFromString(resultSet.getString("role"))
        );
        ChangeType changeType = new ChangeType(
                resultSet.getInt("change_type"),
                resultSet.getString("description")
        );
        return new Product(
                resultSet.getObject("id", UUID.class),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                user,
                resultSet.getDouble("price"),
                resultSet.getBoolean("is_closed"),
                changeType,
                resultSet.getDouble("change_value"),
                resultSet.getString("change_wish")
        );
    }


    public boolean delete(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(DELETE_SQL)) {
            deleteStatement.setString(1, id.toString());
            int i = deleteStatement.executeUpdate();
            return i == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }


}
