package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.dto.ProductDto;
import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.services.ProductService;
import com.dudev.jdbc.starter.services.UserService;
import com.dudev.jdbc.starter.util.ConstantUtil;
import com.dudev.jdbc.starter.util.JSPHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.dudev.jdbc.starter.util.ConstantUtil.*;
import static com.dudev.jdbc.starter.util.UrlPath.ACCOUNT;
import static com.dudev.jdbc.starter.util.UrlPath.PASSWORD;
import static com.dudev.jdbc.starter.util.UrlPath.USERNAME;

@WebServlet(ACCOUNT)
public class AccountServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user =(UserDto) req.getSession().getAttribute("user");
        List<ProductDto> userProducts = productService.findProductsByUser(UUID.fromString(user.getId()), INITIAL_OFFSET, true);
        req.setAttribute("products", userProducts);
        req.getRequestDispatcher(JSPHelper.getPath("account"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("parameter").equals("password")) {
            resp.sendRedirect(PASSWORD);
        } else {
            resp.sendRedirect(USERNAME);
        }
    }
}
