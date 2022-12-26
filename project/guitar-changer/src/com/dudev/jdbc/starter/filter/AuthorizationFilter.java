package com.dudev.jdbc.starter.filter;

import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.entity.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static com.dudev.jdbc.starter.entity.Role.*;
import static com.dudev.jdbc.starter.util.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATH = Set.of(LOGIN, REGISTRATION, IMAGES);
    private static final Set<String> ADMIN_PATH = Set.of(USERS);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isPublic(requestURI) || isUserLogged(servletRequest)) {
            if (isAdministration(requestURI)) {
                if (isAdmin(servletRequest)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    String prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");
                    ((HttpServletResponse) servletResponse).sendRedirect(prevPage == null ? LOGIN : prevPage);
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String prevPage = ((HttpServletResponse) servletResponse).getHeader("referer");
            ((HttpServletResponse) servletResponse).sendRedirect(prevPage == null ? LOGIN : prevPage);
        }
    }

    private boolean isAdmin(ServletRequest servletRequest) {
        UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return find(user.getRole())
                .orElse(USER) == ADMIN;
    }

    private boolean isAdministration(String requestURI) {
        return ADMIN_PATH.stream().anyMatch(requestURI::startsWith);
    }

    private boolean isUserLogged(ServletRequest servletRequest) {
        return ((HttpServletRequest) servletRequest).getSession().getAttribute("user") != null;
    }

    private boolean isPublic(String requestURI) {
        return PUBLIC_PATH.stream()
                .anyMatch(requestURI::startsWith);
    }
}
