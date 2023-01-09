<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 18.12.2022
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="background-color: lavender">
<%@include file="header.jsp"%>
<h1><fmt:message key="page.users.users"/> :</h1>
<ul>
    <c:forEach items="${requestScope.users}" var="user">
        <li>
            <a href="/user-products?userId=${user.id}&offset=0">${user.username}, ${user.firstName}, ${user.lastName}, ${user.role}</a>
        </li>
    </c:forEach>
</ul>
<a><fmt:message key="page.users.delete"/></a>
<form method="post" action="/users">
    <select name="userId">
        <c:forEach items="${requestScope.users}" var="user">
            <option value="${user.id}">${user.username}</option>
        </c:forEach>
    </select>
    <button type="submit"><fmt:message key="page.users.button.delete"/></button>
    <c:if test="${not empty requestScope.deletedUser}">
        <div style="color: cadetblue"><fmt:message key="page.users.user"/> ${requestScope.deletedUser.username} <fmt:message key="page.users.wasDeleted"/></div>
    </c:if>
</form>
</body>
</html>
