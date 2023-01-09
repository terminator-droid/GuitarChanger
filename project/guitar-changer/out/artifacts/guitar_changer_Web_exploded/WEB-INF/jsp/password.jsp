<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 27.12.2022
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Change password</title>
</head>
<body style="background-color: lavender">
<%@ include file="header.jsp"%>
<form action="/password" method="post">
    <label for="currentPassword"><fmt:message key="current.password"/> :
        <input type="password" name="currentPassword" id="currentPassword">
        <c:if test="${not empty requestScope.passwordError}">
            <span style="color: red">${requestScope.passwordError}</span>
        </c:if>
    </label><br>
    <label for="password"><fmt:message key="new.password"/>:
        <input type="password" name="password" id="password">
    </label><br>
    <c:if test="${not empty requestScope.isChanged}">
        <div>
            <span style="color: cadetblue">${requestScope.isChanged}</span>
        </div>
    </c:if>
    <button type="submit"><fmt:message key="page.registration.send"/></button>
</form>
</body>
</html>
