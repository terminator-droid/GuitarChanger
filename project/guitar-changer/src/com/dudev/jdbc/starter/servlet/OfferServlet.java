package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.dto.Dto;
import com.dudev.jdbc.starter.dto.OfferDto;
import com.dudev.jdbc.starter.dto.ProductDto;
import com.dudev.jdbc.starter.services.ChangeTypeService;
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

import static com.dudev.jdbc.starter.util.ConstantUtil.PAY_TO_BUYER;
import static com.dudev.jdbc.starter.util.UrlPath.OFFER;

@WebServlet(OFFER)
public class OfferServlet extends HttpServlet {

    private final OffersService offersService = OffersService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private static final ChangeTypeService changeTypeService = ChangeTypeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID offerId = req.getParameter("offerId") == null
                ? UUID.fromString((String) req.getServletContext().getAttribute("offerId"))
                : UUID.fromString(req.getParameter("offerId"));
        OfferDto offer = offersService.getOfferById(offerId);
        Dto interchange = productService.findById(offer.getInterchange());
        List<ProductDto> offerProducts = offersService.getOfferProducts(offerId);
        double paymentDelta = interchange.getPrice() - offerProducts.stream().mapToDouble(ProductDto::getPrice).sum();

        paymentDelta += changeTypeService.changeType(offer.getChangeType()).getChangeType() == PAY_TO_BUYER
                ? offer.getChangeValue()
                : offer.getChangeValue() * -1;

        req.setAttribute("paymentDelta", paymentDelta);
            if (offer.isAccepted()) {
                req.setAttribute("accepted", true);
            }
            req.setAttribute("offer", offer);
            req.setAttribute("interchange", interchange);
            req.setAttribute("offerProducts", offerProducts);
            req.getRequestDispatcher(JSPHelper.getPath("offer"))
                    .forward(req, resp);
        }

        @Override
        protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String deleteOffer = req.getParameter("deleteOffer");
            if (deleteOffer != null) {
                offersService.deleteOffer(UUID.fromString(deleteOffer));
                req.setAttribute("toOffers", true);
                req.getRequestDispatcher(JSPHelper.getPath("/user-offers")).forward(req, resp);
            } else {
                UUID offerId = UUID.fromString(req.getParameter("offer"));
                offersService.acceptOffer(offerId);
                req.getServletContext().setAttribute("offerId", offerId);
                req.setAttribute("offerId", offerId);
                doGet(req, resp);
            }
        }
    }
