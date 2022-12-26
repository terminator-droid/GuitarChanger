package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.dto.Dto;
import com.dudev.jdbc.starter.dto.GuitarDto;
import com.dudev.jdbc.starter.dto.PedalDto;
import com.dudev.jdbc.starter.services.ProductService;
import com.dudev.jdbc.starter.util.JSPHelper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    private final ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Dto product = productService.findById(UUID.fromString(id));
        if (product == null) {
            req.setAttribute("error", "NOT FOUND");
            req.getRequestDispatcher(JSPHelper.getPath("error"))
                    .forward(req, resp);
        }
        req.setAttribute("product", product);

        String path = null;
        if (product instanceof GuitarDto) {
            path = JSPHelper.getPath("guitar");
        } else {
            path = JSPHelper.getPath("pedal");
        }

        req.getRequestDispatcher(path)
                .forward(req, resp);
    }
}
