package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.entity.Role;
import com.dudev.jdbc.starter.services.UserService;
import com.dudev.jdbc.starter.util.JSPHelper;
import com.dudev.jdbc.starter.util.PropertiesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.jdbc.PreferQueryMode;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

@WebServlet("/registration")
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
        req.getServletContext().setAttribute("parametersMap", req.getParameterMap());
        if (req.getParameter("role").equals(Role.Admin.name())) {
            resp.sendRedirect("/administration");
        } else {
            userService.addUser(req.getParameter("firstName"), req.getParameter("lastName"),
                    req.getParameter("phoneNumber"),
                    req.getParameter("password"), req.getParameter("address"),
                    req.getParameter("role"), req.getParameter("username"));
            resp.sendRedirect("/products");
        }
    }

}
