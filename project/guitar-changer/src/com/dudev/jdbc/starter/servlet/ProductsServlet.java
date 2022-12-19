package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    private static final ProductService productService = ProductService.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write("<h1>Available products</h1>");
            printWriter.write("<ul>");
            productService.getAllProducts().forEach(productDto -> {
                printWriter.write("""
                        <li>
                            <a href="/product?id=%s">Brand - %s, model - %s,price - %f
                        </li>
                        """.formatted(productDto.id(), productDto.brand(), productDto.model(), productDto.price()));
            });
            printWriter.write("</ul>");
        }
    }
}
