package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.entity.User;
import com.dudev.jdbc.starter.services.UserService;
import com.dudev.jdbc.starter.util.JSPHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private static final UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto deletedUser = null;
        try {
            deletedUser = userService.deleteUser(UUID.fromString(req.getParameter("userId")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("deletedUser", deletedUser);
        req.setAttribute("users", userService.getAllUsers());
        req.getRequestDispatcher(JSPHelper.getPath("users"))
                .forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", userService.getAllUsers());
        req.getRequestDispatcher(JSPHelper.getPath("users"))
                .forward(req, resp);
    }
}
