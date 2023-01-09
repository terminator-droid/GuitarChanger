package com.dudev.jdbc.starter.mapper;

import com.dudev.jdbc.starter.dao.ChangeTypeDao;
import com.dudev.jdbc.starter.dao.ProductDao;
import com.dudev.jdbc.starter.dao.UserDao;
import com.dudev.jdbc.starter.dto.OfferDto;
import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.entity.Offer;
import com.dudev.jdbc.starter.services.UserService;
import com.oracle.wls.shaded.org.apache.bcel.generic.INSTANCEOF;

import java.util.UUID;

public class OfferMapper implements Mapper<Offer, OfferDto>{

    private final ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();
    private final ProductDao productDao = ProductDao.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private static final OfferMapper INSTANCE = new OfferMapper();
    private static final UserService userService = UserService.getInstance();

    public static OfferMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public OfferDto mapFrom(Offer offer) {
        String  username = userService.findById(offer.getBuyer().getId()).getUsername();
        return OfferDto.builder()
                .id(offer.getId())
                .timestamp(offer.getTimestamp())
                .changeValue(offer.getChangeValue())
                .interchange(offer.getInterchange().getId())
                .changeType(offer.getChangeType().getDescription())
                .buyer(offer.getBuyer().getId())
                .buyerName(username)
                .accepted(offer.isAccepted())
                .build();
    }

//    public Offer mapFrom(OfferDto offer) {
//        return Offer.builder()
//                .timestamp(offer.getTimestamp())
//                .changeValue(offer.getChangeValue())
//                .changeType(changeTypeDao.findById(offer.getChangeType()).orElse(null))
//                .interchange(productDao.findById(offer.getInterchange()).orElse(null))
//                .buyer(userDao.findById(offer.getBuyer()).orElse(null))
//                .id(offer.getId())
//                .build();
//    }
}
