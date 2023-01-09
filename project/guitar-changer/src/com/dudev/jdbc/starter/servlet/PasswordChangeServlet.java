package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.services.UserService;
import com.dudev.jdbc.starter.util.JSPHelper;
import com.dudev.jdbc.starter.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static com.dudev.jdbc.starter.util.UrlPath.*;

@WebServlet(PASSWORD)
public class PasswordChangeServlet extends HttpServlet {


    private final UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPHelper.getPath("password"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user =(UserDto) req.getSession().getAttribute("user");
        boolean changePasswordResult = userService.changePassword(req.getParameter("currentPassword"), req.getParameter("password"));
        if (!changePasswordResult) {
            req.setAttribute("passwordError", "Invalid password");
        } else {
            req.setAttribute("isChanged", "Password is changed");
        }
        doGet(req, resp);
    }
}
