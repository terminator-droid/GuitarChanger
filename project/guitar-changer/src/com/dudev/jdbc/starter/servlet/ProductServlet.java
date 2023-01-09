package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.dto.Dto;
import com.dudev.jdbc.starter.dto.GuitarDto;
import com.dudev.jdbc.starter.dto.OfferDto;
import com.dudev.jdbc.starter.dto.SuitableProductDto;
import com.dudev.jdbc.starter.services.OffersService;
import com.dudev.jdbc.starter.services.ProductService;
import com.dudev.jdbc.starter.util.JSPHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    private final ProductService productService = ProductService.getInstance();
    private final OffersService offersService = OffersService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPHelper.getPath("guitar"))
                .forward(req, resp);
//        String id = req.getParameter("id");
//        Dto product = productService.findById(UUID.fromString(id));
//        List<SuitableProductDto> suitableProducts = productService.getSuitableProducts(UUID.fromString(id));
//        req.setAttribute("suitableProducts", suitableProducts);
//        if (product == null) {
//            req.setAttribute("error", "NOT FOUND");
//            req.getRequestDispatcher(JSPHelper.getPath("error"))
//                    .forward(req, resp);
//        }
//        req.setAttribute("product", product);
//        if (product != null) {
//            if (product.isClosed()) {
//                OfferDto offerFromProduct = offersService.getOfferFromProduct(product.getId());
//                if (offerFromProduct == null) {
//                    offerFromProduct = offersService.getOffersByProduct(product.getId()).stream().findFirst().orElse(null);
//                }
//                req.setAttribute("closingOffer", offerFromProduct);
//            }
//            req.setAttribute("offers", offersService.getOffersByProduct(product.getId()));
//        }
//        String path = null;
//        if (product instanceof GuitarDto) {
//            path = JSPHelper.getPath("guitar");
//        } else {
//            path = JSPHelper.getPath("pedal");
//        }
//        req.getRequestDispatcher(path)
//                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteProduct = req.getParameter("deleteProduct");
        productService.deleteProduct(UUID.fromString(deleteProduct));
        req.getRequestDispatcher(JSPHelper.getPath("/account"))
                .forward(req, resp);
    }
}
