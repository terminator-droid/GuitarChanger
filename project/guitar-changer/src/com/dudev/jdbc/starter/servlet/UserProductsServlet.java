package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.services.ProductService;
import com.dudev.jdbc.starter.services.UserService;
import com.dudev.jdbc.starter.util.JSPHelper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/user-products")
public class    UserProductsServlet extends HttpServlet {

    private static final ProductService productService = ProductService.getInstance();
    private static final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user = userService.findById(UUID.fromString(req.getParameter("userId")));
        req.setAttribute("user", user);
        req.setAttribute("products", productService.findProductsByUser(UUID.fromString(req.getParameter("userId")),
                Integer.parseInt(req.getParameter("offset"))));
        RequestDispatcher dispatcher = req.getRequestDispatcher(JSPHelper.getPath("products"));
        dispatcher.forward(req, resp);
    }
}
