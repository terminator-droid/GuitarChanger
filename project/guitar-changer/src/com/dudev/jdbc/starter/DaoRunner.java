package com.dudev.jdbc.starter;

import com.dudev.jdbc.starter.dao.ChangeTypeDao;
import com.dudev.jdbc.starter.dao.ProductDao;
import com.dudev.jdbc.starter.dao.UserDao;
import com.dudev.jdbc.starter.dto.Dto;
import com.dudev.jdbc.starter.dto.ProductDto;
import com.dudev.jdbc.starter.dto.UserFilter;
import com.dudev.jdbc.starter.services.ProductService;
import com.dudev.jdbc.starter.services.UserService;
import com.dudev.jdbc.starter.util.PhoneNumberFormatter;

import java.util.List;
import java.util.SortedMap;
import java.util.UUID;

public class DaoRunner {

    public static void main(String[] args) {
        ProductDao daoInstance = ProductDao.getInstance();
        ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();


        System.out.println(UserService.getInstance()
                .login("grover_12", "fqwefgy245").orElse(null));
//        System.out.println(PhoneNumberFormatter.format("892938"));
//        System.out.println(PhoneNumberFormatter.isValid("+79281302938"));
//        System.out.println(PhoneNumberFormatter.format("+7 962 742 78 54"));
//        System.out.println(UserDao.getInstance().findAll(UserFilter.builder()
//                        .phoneNumber(PhoneNumberFormatter.format("+7 962 742 78 54"))
//                .build()));
//
//        ProductService instance = ProductService.getInstance();
//        List<ProductDto> allProducts = instance.getAllProducts();
//        System.out.println(allProducts);
//        ProductService instance1 = ProductService.getInstance();

//        UserService instance = UserService.getInstance();
//        System.out.println(instance1.findProductsByUser(UUID.fromString("42457a09-e3b3-43cf-8bba-2dceef404f99"), 0));
//        Dto byId = instance1.findById(UUID.fromString("79032a47-8893-4353-a45b-e3fbdff163fb"));
//        System.out.println(instance1.getAllProducts());
//        ProductFilter filter = new ProductFilter(2, 0, UUID.fromString("67805d38-d903-44c7-89e4-fd67047b8e33"), 0, false, 0, 0, null);
//
//        List<Product> all = daoInstance.findAll(filter);
//        System.out.println(all);
//        List<String> allUsersByProducts = ProductsUtil.getAllUsersByProducts();
//        System.out.println(allUsersByProducts);
//        List<Product> all = daoInstance.findAll();
//        System.out.println(all);
//        System.out.println(ProductService.getAllProducts());
//        System.out.println(daoInstance.findByPriceAndChangeType(30000, changeTypeDao.findById(2).orElse(null), 1));
    }
}
