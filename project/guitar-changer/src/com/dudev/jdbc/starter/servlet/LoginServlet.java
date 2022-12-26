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
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Optional;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {

    private static final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Optional<UserDto> loginResult = userService.login(username, password);
        loginResult.ifPresentOrElse(
                userDto -> onLoginSuccess(req, resp, userDto)
                , () -> onLoginFail(req, resp));
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        req.getServletContext().setAttribute("error", true);
        req.getServletContext().setAttribute("username", req.getParameter("username"));
        resp.sendRedirect("/login");
    }

    @SneakyThrows
    private static void onLoginSuccess(HttpServletRequest req, HttpServletResponse resp, UserDto userDto) {
        req.getSession().setAttribute("user", userDto);
        resp.sendRedirect("/products");
    }
}
