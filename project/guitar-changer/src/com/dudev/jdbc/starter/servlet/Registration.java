package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.dto.CreateUserDto;
import com.dudev.jdbc.starter.entity.Role;
import com.dudev.jdbc.starter.exception.ValidationException;
import com.dudev.jdbc.starter.services.UserService;
import com.dudev.jdbc.starter.util.JSPHelper;
import com.dudev.jdbc.starter.util.PropertiesUtil;
import com.dudev.jdbc.starter.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.jdbc.PreferQueryMode;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

@WebServlet(UrlPath.REGISTRATION)
public class Registration extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.getRequestDispatcher(JSPHelper.getPath("registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getServletContext().setAttribute("parametersMap", req.getParameterMap());
        req.getServletContext().setAttribute("firstName", req.getParameter("firstName"));
        req.getServletContext().setAttribute("lastName", req.getParameter("lastName"));
        req.getServletContext().setAttribute("username", req.getParameter("username"));
        req.getServletContext().setAttribute("phoneNumber", req.getParameter("phoneNumber"));
        req.getServletContext().setAttribute("password", req.getParameter("password"));
        req.getServletContext().setAttribute("address", req.getParameter("address"));
        req.getServletContext().setAttribute("role", req.getParameter("role"));
        if (req.getParameter("role").equals(Role.ADMIN.name())) {
                resp.sendRedirect("/administration");
        } else {
            try {
                userService.createUser(
                        CreateUserDto.builder()
                                .address(req.getParameter("address"))
                                .phoneNumber(req.getParameter("phoneNumber"))
                                .firstname(req.getParameter("firstName"))
                                .password(req.getParameter("password"))
                                .role(req.getParameter("role"))
                                .username(req.getParameter("username"))
                                .lastName(req.getParameter("lastName"))
                                .build());
                resp.sendRedirect("/login");
            } catch (ValidationException exception) {
                req.setAttribute("errors", exception.getErrors());
                doGet(req, resp);
            }
        }
    }
}
