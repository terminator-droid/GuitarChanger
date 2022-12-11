package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.dto.PedalFilter;
import com.dudev.jdbc.starter.entity.*;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.joining;

public class PedalDao implements Dao<UUID, Pedal> {

    private static final PedalDao INSTANCE = new PedalDao();
    private static final ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();

    private static final BrandDao brandDao = BrandDao.getInstance();

    private static final UserDao userDao = UserDao.getInstance();
    private static final String FIND_ALL_SQL = """
            SELECT id, model, id, model, description, media_name, timestamp,
            user_id, price, brand, is_closed, change_type, change_value, change_wish
            from project.pedals
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ? :: uuid
            """;

    private static final String DELETE_SQL = """
            DELETE FROM pedals
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO pedals (model, descriptionmedia_name, timestamp, user_id, price, brand, change_type, change_value, change_wish)
            VALUES
            (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE pedals
            set 
                model = ?,
                description = ?,
                year = ?,
                country = ?,
                pick_ups = ?,
                fingerboard_wood = ?,
                media_name = ?,
                timestamp = ?,
                user_id = ?,
                price = ?,
                brand = ?,
                is_closed = ?,
                change_type = ?,
                change_value = ?,
                change_wish = ?
            WHERE id = ?          
            """;

    public List<Pedal> findByPriceAndChangeType(double price, ChangeType changeType, int priceDirection) {
        char compareSign = switch (priceDirection) {
            case 1 -> '>';
            case 2 -> '<';
            default -> '=';
        };
        String whereSql = """
                 WHERE pedals.change_type = ? 
                 AND pedals.price %s ?
                                
                """.formatted(compareSign);
        String sql = FIND_ALL_SQL + whereSql;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, changeType.changeType());
            preparedStatement.setDouble(2, price);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Pedal> pedals = new ArrayList<>();
            while (resultSet.next()) {
                pedals.add(createPedal(resultSet));
            }
            return pedals;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PedalDao() {
    }

    public static PedalDao getInstance() {
        return INSTANCE;
    }

    public List<Pedal> findAll(PedalFilter filter) {
        List<String> whereClauses = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (filter.model() != null) {
            parameters.add("%" + filter.model() + "%");
            whereClauses.add(" model like ?");
        }

        parameters.add(filter.isClosed());
        whereClauses.add(" is_closed = ?");
        if (filter.userId() != null) {
            parameters.add(filter.userId());
            whereClauses.add(" user_is = ?");
        }
        if (filter.brand() != null) {
            parameters.add(filter.brand());
            whereClauses.add(" brand = ?");
        }
        if (filter.price() != 0) {
            parameters.add(filter.price());
            whereClauses.add(" price = ?");
        }
        if (filter.changeType() != 0) {
            parameters.add(filter.changeType());
            whereClauses.add(" change_type = ?");
        }
        if (filter.changeValue() != 0) {
            parameters.add(filter.changeValue());
            whereClauses.add(" change_value = ?");
        }
        if (filter.changeWish() != null) {
            parameters.add("%" + filter.changeWish() + "%");
            whereClauses.add(" change_wish like ");
        }

        String whereSQL = whereClauses.stream().collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));
        String sql = FIND_ALL_SQL + whereSQL;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Pedal> pedals = new ArrayList<>();
            while (resultSet.next()) {
                pedals.add(createPedal(resultSet));
            }
            return pedals;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<Pedal> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Pedal> pedals = new ArrayList<>();
            while (resultSet.next()) {
                pedals.add(createPedal(resultSet));
            }
            return pedals;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Pedal> findById(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            Pedal pedal = null;
            if (resultSet.next()) {
                pedal = createPedal(resultSet);
            }
            return Optional.ofNullable(pedal);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Pedal pedal) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, pedal.getId().toString());
            preparedStatement.setString(2, pedal.getDescription());
            preparedStatement.setString(3, pedal.getMedia());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(pedal.getTimestamp()));
            preparedStatement.setString(5, String.valueOf(pedal.getUser().id()));
            preparedStatement.setDouble(6, pedal.getPrice());
            preparedStatement.setString(7, pedal.getBrand().name());
            preparedStatement.setBoolean(8, pedal.isClosed());
            preparedStatement.setInt(9, pedal.getChangeType().changeType());
            preparedStatement.setDouble(10, pedal.getChangeValue());
            preparedStatement.setString(11, pedal.getChangeWish());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Pedal save(Pedal pedal) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, pedal.getModel());
            preparedStatement.setString(2, pedal.getDescription());
            preparedStatement.setString(3, pedal.getMedia());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(pedal.getTimestamp()));
            preparedStatement.setString(5, pedal.getUser().id().toString());
            preparedStatement.setDouble(6, pedal.getPrice());
            preparedStatement.setString(7, pedal.getBrand().name());
            preparedStatement.setBoolean(8, pedal.isClosed());
            preparedStatement.setInt(9, pedal.getChangeType().changeType());
            preparedStatement.setDouble(10, pedal.getChangeValue());
            preparedStatement.setString(11, pedal.getChangeWish());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            UUID id = null;
            while (generatedKeys.next()) {
                id = UUID.fromString(generatedKeys.getString("id"));
            }
            pedal.setId(id);
            return pedal;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Pedal createPedal(ResultSet resultSet) throws SQLException {
        ChangeType changeType = changeTypeDao.findById(resultSet.getInt("change_type")).orElse(null);
        Brand brand = brandDao.findByName(resultSet.getString("brand")).orElse(null);
        User user = userDao.findById(UUID.fromString(resultSet.getString("user_id"))).orElse(null);
        return new Pedal(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("model"),
                resultSet.getString("description"),
                resultSet.getString("media_name"),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                user,
                resultSet.getDouble("price"),
                brand,
                resultSet.getBoolean("is_closed"),
                changeType,
                resultSet.getDouble("change_value"),
                resultSet.getString("change_wish")
        );
    }
}
