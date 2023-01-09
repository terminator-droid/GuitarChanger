package com.dudev.jdbc.starter.dao;

import com.dudev.jdbc.starter.dto.OfferDto;
import com.dudev.jdbc.starter.entity.ChangeType;
import com.dudev.jdbc.starter.entity.Offer;
import com.dudev.jdbc.starter.entity.Product;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.exception.DaoException;
import com.dudev.jdbc.starter.mapper.OfferMapper;
import com.dudev.jdbc.starter.util.ConnectionManager;
import lombok.SneakyThrows;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.sql.Statement.*;

public class OfferDao implements Dao<UUID, Offer> {

    private static final OfferDao INSTANCE = new OfferDao();
    private static final UserDao userDao = UserDao.getInstance();
    private static final ProductDao productDao = ProductDao.getInstance();
    private static final ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();


    private static final String DELETE_BY_INTERCHANGE = """
            DELETE FROM project.offers
            WHERE interchange = ?
            """;
    private static final String FIND_OFFER_BY_PRODUCT = """
            SELECT offer_id from project.offers_products
            WHERE product_id = ? ::uuid
            """;
    private static final String DELETE_BY_PRODUCT = """
            DELETE FROM project.offers_products 
            WHERE product_id = ? 
            AND offer_id != ?
            """;
    private static final String ACCEPT = """
            UPDATE project.offers SET accepted = true
            WHERE id = ?:: uuid
            """;
    private static final String FIND_ALL = """
            SELECT *  from project.offers
            """;
    private static final String FIND_BY_ID = FIND_ALL + """
            WHERE id = ? :: UUID
            """;
    private static final String FIND_BY_INTERCHANGE = """
            SELECT * FROM project.offers
            WHERE interchange = ? :: uuid
            """;

    private static final String INSERT = """
            INSERT INTO project.offers (buyer, interchange, change_type, change_value, timestamp) VALUES 
                        (?, ?, ?, ?, ?)
            """;
    private static final String INSERT_OFFERS_PRODUCTS = """
            INSERT INTO project.offers_products (offer_id, product_id) VALUES 
            (?, ?)
            """;
    private static final String DELETE_OFFERS_PRODUCTS = """
            DELETE FROM project.offers_products WHERE offer_id = ? ::uuid
            """;
    private static final String UPDATE = """
            UPDATE project.offers SET 
                id = ?,
                buyer = ?,
                interchange = ?,
                change_value = ?,
                change_type = ?,
                change_value = ?,
                timestamp = ?
            """;

    private static final String DELETE = """
            DELETE FROM project.offers WHERE id = ? :: uuid
            """;
    private static final String GET_OFFER_PRODUCTS = """
            SELECT * FROM project.offers_products
            WHERE offer_id = ? :: uuid
            """;
    private static final String FIND_OFFERS_BY_USER = """
             WHERE buyer = ? :: uuid 
            """;

    private OfferDao() {
    }

    public static OfferDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public void deleteByInterchange(UUID interchange, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_BY_INTERCHANGE);
            preparedStatement.setObject(1, interchange);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public Optional<Offer> findOfferByProduct(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_OFFER_BY_PRODUCT)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Offer offer = null;
            if (resultSet.next()) {
                offer = findById(UUID.fromString(resultSet.getString("offer_id"))).orElse(null);
            }
            return Optional.ofNullable(offer);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @SneakyThrows
    public void deleteByProduct(UUID id, Connection connection, UUID offerId) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_BY_PRODUCT);
            preparedStatement.setObject(1, id);
            preparedStatement.setObject(2, offerId);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @SneakyThrows
    public void acceptOffer(UUID id, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(ACCEPT);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @SneakyThrows
    public void acceptOffer(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ACCEPT)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        }
    }

    public List<Offer> getOffersByUser(UUID user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL + FIND_OFFERS_BY_USER)) {
            preparedStatement.setString(1, user.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Offer> offers = new ArrayList<>();
            while (resultSet.next()) {
                offers.add(createOffer(resultSet));
            }
            return offers;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<UUID> getProductsFromOffer(UUID offerId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_OFFER_PRODUCTS)) {
            preparedStatement.setString(1, offerId.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<UUID> productsUUID = new ArrayList<>();
            while (resultSet.next()) {
                productsUUID.add(UUID.fromString(resultSet.getString("product_id")));
            }
            return productsUUID;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Offer> getByInterchange(UUID interchange) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_INTERCHANGE)) {
            preparedStatement.setString(1, interchange.toString());
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

    @SneakyThrows
    public void delete(UUID id, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            deleteOffersProducts(id, connection);
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @SneakyThrows
    private void deleteOffersProducts(UUID id, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_OFFERS_PRODUCTS);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
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
            preparedStatement.setInt(4, offer.getChangeType().getChangeType());
            preparedStatement.setDouble(5, offer.getChangeValue());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(offer.getTimestamp()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @SneakyThrows
    public UUID save(Offer offer, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, offer.getBuyer().getId());
            preparedStatement.setObject(2, offer.getInterchange().getId());
            preparedStatement.setInt(3, offer.getChangeType().getChangeType());
            preparedStatement.setDouble(4, offer.getChangeValue());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(offer.getTimestamp()));

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            UUID offerId = null;
            if (generatedKeys.next()) {
                offerId = UUID.fromString(generatedKeys.getString("id"));
            }
            return offerId;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @SneakyThrows
    public void saveOfferProduct(UUID offerId, UUID productId, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_OFFERS_PRODUCTS);
            preparedStatement.setObject(1, offerId);
            preparedStatement.setObject(2, productId);

            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Override
    public Offer save(Offer offer) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, offer.getBuyer().getId());
            preparedStatement.setObject(2, offer.getInterchange().getId());
            preparedStatement.setInt(3, offer.getChangeType().getChangeType());
            preparedStatement.setDouble(4, offer.getChangeValue());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(offer.getTimestamp()));

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            Offer currentOffer = offer;
            while (generatedKeys.next()) {
                currentOffer.setId(UUID.fromString(generatedKeys.getString("id")));
            }
//            insertOffersProducts(currentOffer.getId(), currentOffer.getInterchange().getId());
            return currentOffer;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Offer createOffer(ResultSet resultSet) throws SQLException {
        User buyer = userDao.findById(UUID.fromString(resultSet.getString("buyer"))).orElse(null);
        Product interchange = productDao.findById(UUID.fromString(resultSet.getString("interchange"))).orElse(null);
        ChangeType changeType = changeTypeDao.findById(resultSet.getInt("change_type")).orElse(null);
        return Offer.builder()
                .id(UUID.fromString(resultSet.getString("id")))
                .buyer(buyer)
                .interchange(interchange)
                .changeType(changeType)
                .changeValue(resultSet.getDouble("change_value"))
                .timestamp(resultSet.getTimestamp("timestamp").toLocalDateTime())
                .accepted(resultSet.getBoolean("accepted"))
                .build();
    }
}
