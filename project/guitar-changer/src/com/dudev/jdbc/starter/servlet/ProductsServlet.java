package com.dudev.jdbc.starter.servlet;

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
@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    private static final ProductService productService = ProductService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute("products", productService.getAllProducts());
        RequestDispatcher dispatcher = req.getRequestDispatcher(JSPHelper.getPath("products"));
        dispatcher.forward(req, resp);
    }
}
