<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 18.12.2022
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="background-color: lavender">
<h1><fmt:message key="page.registration"/></h1>
<img width="200" height="100" src="${pageContext.request.contextPath}/images/registration_guitar.png" alt="User image">
<form action="/registration" method="post">
    <label for="firstName"><fmt:message key="page.registration.firstName"/>:
        <input type="text" name="firstName" id="firstName">
    </label><br>
    <label for="lastName"><fmt:message key="page.registration.lastName"/>:
        <input type="text" name="lastName" id="lastName">
    </label><br>
    <label for="username"><fmt:message key="page.registration.username"/>:
        <input type="text" name="username" id="username">
        <c:if test="${not empty requestScope.errors}">
                <span style="color: red">
                    <c:forEach var="error" items="${requestScope.errors}">
                        <c:if test="${error.code == '404.username.null'
                        || error.code == '400.username'
                        || error.code == '409.username'}">${error.message}</c:if>
                    </c:forEach>
                </span>
        </c:if>
    </label><br>
    <label for="phoneNumber"><fmt:message key="page.registration.phoneNumber"/>:
        <input type="text" name="phoneNumber" id="phoneNumber">
        <c:if test="${not empty requestScope.errors}">
                <span style="color: red">
                    <c:forEach var="error" items="${requestScope.errors}">
                        <c:if test="${error.code == '404.phoneNumber.null'
                        || error.code == '400.phoneNumber'
                        || error.code == '409.phoneNumber'}">${error.message}</c:if>
                    </c:forEach>
                </span>
        </c:if>
    </label><br>
    <label for="password"><fmt:message key="page.registration.password"/>:
        <input type="password" name="password" id="password">
        <c:if test="${not empty requestScope.errors}">
                <span style="color: red">
                    <c:forEach var="error" items="${requestScope.errors}">
                        <c:if test="${error.code == '404.password.null'
                        || error.code == '400.password'
                        || error.code == '409.password' }">${error.message}</c:if>
                    </c:forEach>
                </span>
        </c:if>
    </label><br>
    <label for="address"><fmt:message key="page.registration.address"/>:
        <input type="text" name="address" id="address">
    </label><br>
    <select name="role" id="role"><fmt:message key="page.registration.role"/>:
        <c:forEach var="role" items="${requestScope.roles}">
            <option value="${role}">${role}</option>
        </c:forEach>
    </select>
    <button type="submit"><fmt:message key="page.registration.send"/></button>
    <%--        <c:if test="${not empty requestScope.errors}">--%>
    <%--            <div style="accent-color: red">--%>
    <%--                <c:forEach var="error" items="${requestScope.errors}">--%>
    <%--                    <span>${error.message}</span>--%>
    <%--                </c:forEach>--%>
    <%--            </div>--%>
    <%--        </c:if>--%>
</form>
</body>
</html>
