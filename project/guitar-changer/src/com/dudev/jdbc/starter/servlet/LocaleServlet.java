package com.dudev.jdbc.starter.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.dudev.jdbc.starter.util.UrlPath.LOCALE;
import static com.dudev.jdbc.starter.util.UrlPath.LOGIN;

@WebServlet(LOCALE)
public class LocaleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");
        req.getSession().setAttribute("lang", lang);

        String referer = req.getHeader("referer");
        var prevPage = referer != null ? referer : LOGIN;
        resp.sendRedirect(referer + "?lang=" + lang);

    }
}
