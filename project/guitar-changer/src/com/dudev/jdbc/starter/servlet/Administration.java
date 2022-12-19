package com.dudev.jdbc.starter.servlet;

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
            req.getRequestDispatcher(JSPHelper.getPath("error"))
                    .forward(req, resp);
        } else {
            Map<String, String[]> params = (Map<String, String[]>) req.getServletContext().getAttribute("parametersMap");
            userService.addUser(params.get("firstName")[0], params.get("lastName")[0], params.get("phoneNumber")[0], params.get("password")[0],
                    params.get("address")[0], params.get("role")[0], params.get("username")[0]);
            resp.sendRedirect("/products");
        }
    }
}
