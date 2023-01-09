package com.dudev.jdbc.starter.services;

import com.dudev.jdbc.starter.dao.ChangeTypeDao;
import com.dudev.jdbc.starter.dao.GuitarDao;
import com.dudev.jdbc.starter.dao.OfferDao;
import com.dudev.jdbc.starter.dao.PedalDao;
import com.dudev.jdbc.starter.dao.ProductDao;
import com.dudev.jdbc.starter.dto.OfferDto;
import com.dudev.jdbc.starter.dto.ProductDto;
import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.entity.Offer;
import com.dudev.jdbc.starter.entity.Product;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.mapper.OfferMapper;
import com.dudev.jdbc.starter.mapper.ProductMapper;
import com.dudev.jdbc.starter.mapper.UserMapper;
import com.dudev.jdbc.starter.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dudev.jdbc.starter.util.ConstantUtil.SAIL;
import static java.util.Comparator.*;

public class OffersService {

    private static final OffersService INSTANCE = new OffersService();

    private final OfferDao offerDao = OfferDao.getInstance();
    private final OfferMapper offerMapper = OfferMapper.getInstance();

    private final ProductDao productDao = ProductDao.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final ProductMapper productMapper = ProductMapper.getInstance();
    private final GuitarDao guitarDao = GuitarDao.getInstance();
    private final PedalDao pedalDao = PedalDao.getInstance();
    private final ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();

    private final UserMapper userMapper = UserMapper.getInstance();

    public static OffersService getInstance() {
        return INSTANCE;
    }

    public OfferDto getOfferById(UUID id) {
        return offerMapper.mapFrom(offerDao.findById(id).orElseThrow());
    }

    @SneakyThrows
    public OfferDto getOfferFromProduct(UUID productId) {
        return offerDao.findOfferByProduct(productId)
                .map(offerMapper::mapFrom)
                .orElse(null);
    }

    @SneakyThrows
    public void deleteOffer(UUID offerId) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            offerDao.delete(offerId, connection);
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }

    }

    @SneakyThrows
    public void acceptOffer(UUID offerId) {
        Offer offer = offerDao.findById(offerId).orElseThrow();
        List<ProductDto> offerProducts = getOfferProducts(offerId);
        UUID productId = offer.getInterchange().getId();
        List<Offer> offers = offerDao.getByInterchange(productId);
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            productDao.closeProduct(productId, connection);
            offerDao.deleteByProduct(productId, connection, null);
            for (ProductDto offerProduct : offerProducts) {
                productDao.closeProduct(offerProduct.getId(), connection);
                offerDao.deleteByProduct(offerProduct.getId(), connection, offer.getId());
            }
            offerDao.acceptOffer(offerId, connection);
            for (Offer interchangeOffer : offers) {
                if (!interchangeOffer.getId().equals(offerId)) {
                    offerDao.delete(interchangeOffer.getId(), connection);
                }
            }
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }

    public List<OfferDto> getOffersByUser(UUID user) {
        return offerDao.getOffersByUser(user)
                .stream()
                .map(offerMapper::mapFrom)
                .peek(offerDto -> offerDto.setInterchangeBrand(productService.findById(offerDto.getInterchange()).getBrand()))
                .peek(offerDto -> offerDto.setInterchangeModel(productService.findById(offerDto.getInterchange()).getModel()))
                .toList();
    }

    @SneakyThrows
    public List<ProductDto> getOfferProducts(UUID offerId) {
        return offerDao.getProductsFromOffer(offerId)
                .stream()
                .map(productId ->
                        productMapper.mapFrom(productDao.findById(productId).orElseThrow()))
                .toList();
    }

    public List<OfferDto> getOffersByProduct(UUID productId) {
        return offerDao.getByInterchange(productId)
                .stream()
                .map(offerMapper::mapFrom)
                .sorted(comparing(OfferDto::getTimestamp)).toList();
    }

    @SneakyThrows
    public void createOfferSail(UserDto buyer, UUID interchange) {
        offerDao.save(Offer.builder()
                .interchange(productDao.findById(interchange).orElseThrow())
                .timestamp(LocalDateTime.now())
                .buyer(userMapper.mapFrom(buyer))
                .changeType(changeTypeDao.findById(SAIL).orElseThrow())
                .build());
    }

    @SneakyThrows
    public void createOfferChange(UserDto user, UUID interchange, List<String> products, int changeType, double payment) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            UUID offerId = offerDao.save(Offer.builder()
                    .changeType(changeTypeDao.findById(changeType).orElseThrow())
                    .buyer(userMapper.mapFrom(user))
                    .changeValue(payment)
                    .timestamp(LocalDateTime.now())
                    .interchange(productDao.findById(interchange).orElseThrow())
                    .build(), connection);
            for (String product : products) {
                offerDao.saveOfferProduct(offerId, UUID.fromString(product), connection);
            }
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }

    public void deleteByProduct(UUID productId, Connection connection) {
        offerDao.deleteByProduct(productId, connection, null);
        offerDao.deleteByInterchange(productId, connection);
    }

}
