package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.entity.ChangeType;
import com.dudev.jdbc.starter.entity.Offer;
import com.dudev.jdbc.starter.entity.Product;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.util.ConnectionManager;

import java.rmi.server.UID;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OfferDao implements Dao<UUID, Offer> {

    private static final OfferDao INSTANCE = new OfferDao();
    private static final UserDao userDao = UserDao.getInstance();
    private static final ProductDao productDao = ProductDao.getInstance();
    private static final ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();
    private static final String FIND_ALL = """
            SELECT id, buyer, interchange, change_type, change_value, timestamp from offers
            """;
    private static final String FIND_BY_ID = FIND_ALL + """
            WHERE id = ?
            """;
    private static final String INSERT = """
            INSERT INTO project.offers (buyer, interchange, change_type, change_value, timestamp) VALUES 
                        (?, ?, ?, ?, ?, ?)
            """;
    private static final String INSERT_OFFERS_PRODUCTS = """
            INSERT INTO offers_products (offer_id, product_id) VALUES 
            (?, ?)
            """;
    private static final String DELETE_OFFERS_PRODUCTS = """
            DELETE FROM offers_products WHERE offer_id = ?
            """;
    private static final String UPDATE = """
            UPDATE offers SET 
                id = ?,
                buyer = ?,
                interchange = ?,
                change_value = ?,
                change_type = ?,
                change_value = ?,
                timestamp = ?
            """;

    private static final String DELETE = """
            DELETE FROM offers WHERE id = ?
            """;

    private OfferDao() {
    }

    public static OfferDao getInstance() {
        return INSTANCE;
    }

    public void delete(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
            deleteOffersProducts(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void insertOffersProducts(UUID offerId, UUID productId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_OFFERS_PRODUCTS)) {
            preparedStatement.setString(1, offerId.toString());
            preparedStatement.setString(2, productId.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void deleteOffersProducts(UUID offerId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_OFFERS_PRODUCTS)) {
            preparedStatement.setString(1, offerId.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Offer> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Offer> offers = new ArrayList<>();
            while (resultSet.next()) {
                offers.add(createOffer(resultSet));
            }
            return offers;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Offer> findById(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            Offer offer = null;
            if (resultSet.next()) {
                offer = createOffer(resultSet);
            }
            return Optional.ofNullable(offer);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Offer offer) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, offer.getId().toString());
            preparedStatement.setString(2, offer.getBuyer().toString());
            preparedStatement.setString(3, offer.getInterchange().toString());
            preparedStatement.setInt(4, offer.getChangeType().changeType());
            preparedStatement.setDouble(5, offer.getChangeValue());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(offer.getTimestamp()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Offer save(Offer offer) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, offer.getBuyer().toString());
            preparedStatement.setString(2, offer.getInterchange().toString());
            preparedStatement.setInt(3, offer.getChangeType().changeType());
            preparedStatement.setDouble(4, offer.getChangeValue());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(offer.getTimestamp()));

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            Offer currentOffer = offer;
            while (generatedKeys.next()) {
                currentOffer.setId(UUID.fromString(generatedKeys.getString("id")));
            }
            insertOffersProducts(currentOffer.getId(), currentOffer.getInterchange().getId());
            return currentOffer;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Offer createOffer(ResultSet resultSet) throws SQLException {
        User buyer = userDao.findById(UUID.fromString(resultSet.getString("buyer"))).orElse(null);
        Product interchange = productDao.findById(UUID.fromString(resultSet.getString("interchange"))).orElse(null);
        ChangeType changeType = changeTypeDao.findById(resultSet.getInt("change_type")).orElse(null);
        return new Offer(
                UUID.fromString(resultSet.getString("id")),
                buyer,
                interchange,
                changeType,
                resultSet.getDouble("change_value"),
                resultSet.getTimestamp("timestamp").toLocalDateTime()
        );
    }
}
