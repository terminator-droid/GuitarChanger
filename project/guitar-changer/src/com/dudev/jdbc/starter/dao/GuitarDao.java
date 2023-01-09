package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.dto.GuitarFilter;
import com.dudev.jdbc.starter.dto.ProductFilter;
import com.dudev.jdbc.starter.entity.*;
import com.dudev.jdbc.starter.dao.ChangeTypeDao;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;
import lombok.SneakyThrows;

import javax.swing.text.html.Option;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class GuitarDao implements Dao<UUID, Guitar> {

    private static final GuitarDao INSTANCE = new GuitarDao();
    private static final ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();

    private static final BrandDao brandDao = BrandDao.getInstance();

    private static final UserDao userDao = UserDao.getInstance();

    private static final String CLOSE = """
            UPDATE project.guitars SET is_closed = true
            WHERE id = ? 
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, model, id, model, description, year,
            country, pick_ups, fingerboard_wood, media_name, timestamp,
            user_id, price, brand, is_closed, change_type, change_value, change_wish
            from project.guitars
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ? :: uuid
            """;

    private static final String DELETE_SQL = """
            DELETE FROM guitars
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO project.guitars (model, description, year, country, pick_ups, fingerboard_wood, media_name, timestamp, user_id, price, brand, is_closed, change_type, change_value, change_wish)
            VALUES
            (?, ?, ?, ?, ?, ?, ?, ?, ? :: uuid, ?, ?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE guitars
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
    private static final String DELETE_GUITARS = """
            DELETE FROM project.guitars       
            """;


    private GuitarDao() {
    }

    public static GuitarDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public void closeGuitar(UUID guitarId, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(CLOSE);
            preparedStatement.setObject(1, guitarId);

            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public List<Guitar> findByPriceAndChangeType(double price, ChangeType changeType, int priceDirection) {
        char compareSign = switch (priceDirection) {
            case 1 -> '>';
            case 2 -> '<';
            default -> '=';
        };
        String whereSql = """
                 WHERE guitars.change_type = ? 
                 AND guitars.price %s ?
                                
                """.formatted(compareSign);
        String sql = FIND_ALL_SQL + whereSql;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, changeType.getChangeType());
            preparedStatement.setDouble(2, price);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Guitar> guitars = new ArrayList<>();
            while (resultSet.next()) {
                guitars.add(createGuitar(resultSet));
            }
            return guitars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Guitar> findAll(GuitarFilter filter) {
        List<String> whereClauses = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (filter.model() != null) {
            parameters.add("%" + filter.model() + "%");
            whereClauses.add(" model like ?");
        }
        if (filter.year() != 0) {
            parameters.add(filter.year());
            whereClauses.add(" year = ?");
        }
        if (filter.country() != null) {
            parameters.add(filter.country());
            whereClauses.add(" country = ?");
        }
        parameters.add(filter.isClosed());
        whereClauses.add(" is_closed = ?");
        if (filter.user() != null) {
            parameters.add(filter.user());
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
            List<Guitar> guitars = new ArrayList<>();
            while (resultSet.next()) {
                guitars.add(createGuitar(resultSet));
            }
            return guitars;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<Guitar> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Guitar> guitars = new ArrayList<>();
            while (resultSet.next()) {
                guitars.add(createGuitar(resultSet));
            }
            return guitars;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Guitar> findById(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            Guitar guitar = null;
            if (resultSet.next()) {
                guitar = createGuitar(resultSet);
            }
            return Optional.ofNullable(guitar);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Guitar guitar) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, guitar.getId().toString());
            preparedStatement.setString(2, guitar.getDescription());
            preparedStatement.setInt(3, guitar.getYear());
            preparedStatement.setString(4, guitar.getPickUps());
            preparedStatement.setString(5, guitar.getWood());
            preparedStatement.setString(6, guitar.getMedia());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(guitar.getTimestamp()));
            preparedStatement.setString(8, String.valueOf(guitar.getUser().getId()));
            preparedStatement.setDouble(9, guitar.getPrice());
            preparedStatement.setString(10, guitar.getBrand().getName());
            preparedStatement.setBoolean(11, guitar.isClosed());
            preparedStatement.setInt(12, guitar.getChangeType().getChangeType());
            preparedStatement.setDouble(13, guitar.getChangeValue());
            preparedStatement.setString(14, guitar.getChangeWish());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Guitar save(Guitar guitar) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, guitar.getModel());
            preparedStatement.setString(2, guitar.getDescription());
            preparedStatement.setInt(3, guitar.getYear());
            preparedStatement.setString(4, guitar.getCountry());
            preparedStatement.setString(5, guitar.getPickUps());
            preparedStatement.setString(6, guitar.getWood());
            preparedStatement.setString(7, guitar.getMedia());
            preparedStatement.setTimestamp(8, Timestamp.valueOf(guitar.getTimestamp()));
            preparedStatement.setString(9, guitar.getUser().getId().toString());
            preparedStatement.setDouble(10, guitar.getPrice());
            preparedStatement.setString(11, guitar.getBrand().getName());
            preparedStatement.setBoolean(12, guitar.isClosed());
            preparedStatement.setInt(13, guitar.getChangeType().getChangeType());
            preparedStatement.setDouble(14, guitar.getChangeValue());
            preparedStatement.setString(15, guitar.getChangeWish());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            UUID id = null;
            while (generatedKeys.next()) {
                id = UUID.fromString(generatedKeys.getString("id"));
            }
            guitar.setId(id);
            return guitar;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @SneakyThrows
    public void delete(GuitarFilter filter) {
        List<String> whereConditions = new ArrayList<>();
        List<Object> fields = new ArrayList<>();
        if (filter.brand() != null) {
            whereConditions.add(" brand = ?");
            fields.add(filter.brand());
        }
        if (filter.changeType() != 0) {
            whereConditions.add(" change_type = ?");
            fields.add(filter.changeType());
        }
        whereConditions.add("is_closed = ?");
        fields.add(filter.isClosed());
        if (filter.user() != null) {
            whereConditions.add(" user_id = ?");
            fields.add(filter.user());
        }
        String sql = DELETE_GUITARS + " WHERE " + String.join(" AND ", whereConditions);

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < whereConditions.size(); i++) {
                preparedStatement.setObject(i + 1, fields.get(i));
            }
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public void delete(GuitarFilter filter, Connection connection) {
        List<String> whereConditions = new ArrayList<>();
        List<Object> fields = new ArrayList<>();
        if (filter.brand() != null) {
            whereConditions.add(" brand = ?");
            fields.add(filter.brand());
        }
        if (filter.changeType() != 0) {
            whereConditions.add(" change_type = ?");
            fields.add(filter.changeType());
        }
        if (filter.isClosed()) {
            whereConditions.add("is_closed = ?");
            fields.add(true);
        }
        if (filter.user() != null) {
            whereConditions.add(" user_id = ?");
            fields.add(filter.user());
        }
        if (filter.id() != null) {
            whereConditions.add(" id = ?");
            fields.add(filter.id());
        }
        String sql = DELETE_GUITARS + " WHERE " + String.join(" AND ", whereConditions);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < whereConditions.size(); i++) {
                preparedStatement.setObject(i + 1, fields.get(i));
            }
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    private Guitar  createGuitar(ResultSet resultSet) throws SQLException {
        ChangeType changeType = changeTypeDao.findById(resultSet.getInt("change_type")).orElse(null);
        Brand brand = brandDao.findByName(resultSet.getString("brand")).orElse(null);
        User user = userDao.findById(UUID.fromString(resultSet.getString("user_id"))).orElse(null);
        return Guitar.builder()
                .id(UUID.fromString(resultSet.getString("id")))
                .brand(brand)
                .changeType(changeType)
                .changeValue(resultSet.getDouble("change_value"))
                .model(resultSet.getString("model"))
                .description(resultSet.getString("description"))
                .changeWish(resultSet.getString("change_wish"))
                .country(resultSet.getString("country"))
                .isClosed(resultSet.getBoolean("is_closed"))
                .pickUps(resultSet.getString("pick_ups"))
                .timestamp(resultSet.getTimestamp("timestamp").toLocalDateTime())
                .user(user)
                .model(resultSet.getString("model"))
                .year(resultSet.getInt("year"))
                .wood(resultSet.getString("fingerboard_wood"))
                .media(resultSet.getString("media_name"))
                .price(resultSet.getDouble("price"))
                .build();
    }
}
