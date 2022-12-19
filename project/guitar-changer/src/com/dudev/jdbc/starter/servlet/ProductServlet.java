package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.dto.Dto;
import com.dudev.jdbc.starter.dto.GuitarDto;
import com.dudev.jdbc.starter.dto.PedalDto;
import com.dudev.jdbc.starter.services.ProductService;
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
        resp.setContentType("text/html");
        resp.setContentType(StandardCharsets.UTF_8.name());
        String id = req.getParameter("id");
        try (PrintWriter writer = resp.getWriter()) {
            Dto product = productService.findById(UUID.fromString(id));
            if (product instanceof GuitarDto) {
                writer.write("<h1>Additional guitar information:</h1>");
                writer.write("""
                        <ul>
                            <li>Year - %s</li>
                            <li>Pick-ups - %s</li>
                            <li>Fingerboard wood - %s</li>
                            <li>Change type - %s</li>
                            <li>Change wish - %s</li>
                            <li>Change value - %f</li>
                        </ul>                    
                        """.formatted(((GuitarDto) product).getYear(), ((GuitarDto) product).getPickUps(),
                        ((GuitarDto) product).getWood(), ((GuitarDto) product).getChangeType(),
                        ((GuitarDto) product).getChangeWish(), ((GuitarDto) product).getChangeValue()));
            } else if (product instanceof PedalDto) {
                writer.write("<h1>Additional pedal information:</h1>");
                writer.write("""
                        <ul>
                            <li>Description - %s</li>
                            <li>Change type - %s</li>
                            <li>Change wish - %s</li>
                            <li>Change value - %f</li>
                        </ul>                    
                        """.formatted(((PedalDto) product).getDescription(), ((PedalDto) product).getChangeType(),
                        ((PedalDto) product).getChangeWish(), ((PedalDto) product).getChangeValue()));
            }
//            String resultProduct = String.join(",", product.getListedFields().toString());
//            writer.write("""
//                    <h1>Выбранный товар:</h1>
//                    <h1>%s</h1>
//                    """.formatted(resultProduct));
//            Arrays.stream(product.getClass().getFields()).map(field -> {
//                try {
//                    return product.getClass().getDeclaredField(field.getName()).get(new Object());
//                } catch (IllegalAccessException | NoSuchFieldException e) {
//                    throw new RuntimeException(e);
//                }
//            }).forEach(value -> writer.write("""
//                    <h1>
//                        %s
//                    </h1>
//                    """.formatted(String.valueOf(value))));
        }
    }
}
