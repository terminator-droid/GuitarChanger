package com.dudev.jdbc.starter.filter;

import com.dudev.jdbc.starter.dto.Dto;
import com.dudev.jdbc.starter.dto.OfferDto;
import com.dudev.jdbc.starter.dto.ProductDto;
import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.entity.ChangeType;
import com.dudev.jdbc.starter.entity.Offer;
import com.dudev.jdbc.starter.entity.Product;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.mapper.ProductMapper;
import com.dudev.jdbc.starter.services.ChangeTypeService;
import com.dudev.jdbc.starter.services.OffersService;
import com.dudev.jdbc.starter.services.ProductService;
import com.dudev.jdbc.starter.util.JSPHelper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.lang.model.element.NestingKind;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.dudev.jdbc.starter.util.ConstantUtil.EXCHANGE;
import static com.dudev.jdbc.starter.util.ConstantUtil.SAIL;

@WebFilter("/offer")
public class OfferFilter implements Filter {


    private final OffersService offersService = OffersService.getInstance();
    private final ProductService productService = ProductService.getInstance();

    private final ChangeTypeService changeTypeService = ChangeTypeService.getInstance();
    private static final ProductMapper productMapper = ProductMapper.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String interchange = servletRequest.getParameter("interchange");
        String changeType = servletRequest.getParameter("changeType");
        UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        if (interchange != null) {
            servletRequest.getServletContext().setAttribute("interchange", interchange);
            servletRequest.setAttribute("changeTypes", changeTypeService.getChangeTypes());
            servletRequest.getRequestDispatcher(JSPHelper.getPath("/offer-create"))
                    .forward(servletRequest, servletResponse);
        } else if (changeType != null) {
            servletRequest.getServletContext().setAttribute("changeType", changeType);
            if (Integer.parseInt(changeType) != SAIL) {
                if (Integer.parseInt(changeType) != EXCHANGE) {
                    servletRequest.setAttribute("withPayment", true);
                }
                List<ProductDto> productsByUser = productService.findProductsByUser(UUID.fromString(user.getId()), 0, false);
                servletRequest.setAttribute("userProducts", productsByUser);
                servletRequest.getRequestDispatcher(JSPHelper.getPath("/offer-create"))
                        .forward(servletRequest, servletResponse);
            } else {
                offersService.createOfferSail(user, UUID.fromString((String) servletRequest.getServletContext().getAttribute("interchange")));

                servletRequest.setAttribute("offers", offersService.getOffersByUser(UUID.fromString(user.getId())));
                servletRequest.getRequestDispatcher(JSPHelper.getPath("/user-offers"))
                        .forward(servletRequest, servletResponse);
            }
        } else if (servletRequest.getParameter("products") != null) {

            List<String> productsIds = Arrays.asList(servletRequest.getParameterValues("products"));
            UUID productToInterchange = UUID.fromString((String) servletRequest.getServletContext().getAttribute("interchange"));
            int changeTypeExchange = Integer.parseInt((String) servletRequest.getServletContext().getAttribute("changeType"));
            double payment = servletRequest.getParameter("payment") != null
                    ? Double.parseDouble(servletRequest.getParameter("payment"))
                    : 0;


            offersService.createOfferChange(user, productToInterchange, productsIds, changeTypeExchange, payment);

            servletRequest.setAttribute("offers", offersService.getOffersByUser(UUID.fromString(user.getId())));
            servletRequest.getRequestDispatcher(JSPHelper.getPath("/user-offers"))
                    .forward(servletRequest, servletResponse);

        } else if (servletRequest.getParameter("toOffers") != null) {
            servletRequest.setAttribute("offers", offersService.getOffersByUser(UUID.fromString(user.getId())));
            servletRequest.getRequestDispatcher(JSPHelper.getPath("/user-offers"))
                    .forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}


