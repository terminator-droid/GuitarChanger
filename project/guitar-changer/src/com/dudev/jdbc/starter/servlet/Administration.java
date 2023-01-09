package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.dto.CreateUserDto;
import com.dudev.jdbc.starter.exception.ValidationException;
import com.dudev.jdbc.starter.services.UserService;
import com.dudev.jdbc.starter.util.JSPHelper;
import com.dudev.jdbc.starter.util.PropertiesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/administration")
public class Administration extends HttpServlet {

    private static final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPHelper.getPath("administration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getParameter("password").equals(PropertiesUtil.get("admin.password")) || !req.getParameter("username").equals(PropertiesUtil.get("admin.username"))) {
            req.setAttribute("error", "Incorrect credentials");
            req.setAttribute("username", req.getParameter("username"));
            req.getRequestDispatcher(JSPHelper.getPath("/administration"))
                    .forward(req, resp);
        } else {
            try {
                createUser(req);
                resp.sendRedirect("/login");
            } catch (ValidationException e) {
                req.setAttribute("errors", e.getErrors());
                req.getRequestDispatcher("/registration")
                        .forward(req, resp);
            }
        }
    }

    private static void createUser(HttpServletRequest req) {
        userService.createUser(CreateUserDto.builder()
                .lastName(req.getServletContext().getAttribute("lastName").toString())
                .firstname(req.getServletContext().getAttribute("firstName").toString())
                .username(req.getServletContext().getAttribute("username").toString())
                .role(req.getServletContext().getAttribute("role").toString())
                .address(req.getServletContext().getAttribute("address").toString())
                .password(req.getServletContext().getAttribute("password").toString())
                .phoneNumber(req.getServletContext().getAttribute("phoneNumber").toString())
                .build());
    }
}
