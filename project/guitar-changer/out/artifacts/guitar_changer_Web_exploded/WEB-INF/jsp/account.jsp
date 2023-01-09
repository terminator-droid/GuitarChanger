<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 27.12.2022
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${sessionScope.user.username}</title>
</head>
<body style="background-color: lavender">
<%@include file="header.jsp" %>
<h1><fmt:message key="account"/> ${sessionScope.user.username}</h1>
<div>
    <span><fmt:message key="page.login.username"/>: ${sessionScope.user.username}</span><br>
    <span><fmt:message key="page.registration.firstName"/>: ${sessionScope.user.firstName}</span><br>
    <span><fmt:message key="page.registration.lastName"/>: ${sessionScope.user.lastName}</span><br>
    <span><fmt:message key="page.registration.role"/>: ${sessionScope.user.role}</span><br>
</div>
<form method="post" action="/account">
    <button type="submit" name="parameter" value="password">Change password</button>
    <button type="submit" name="parameter" value="username">Change username</button>
</form>
<br>
<c:if test="${not empty requestScope.products}">
    <h2><fmt:message key="your.products"/>:</h2>
</c:if>
<ul>
    <c:forEach var="product" items="${requestScope.products}">
        <li>
            <a href="/product?id=${product.id}"><fmt:message key="product.brand"/> - ${product.brand}, <fmt:message key="product.model"/> - ${product.model}, <fmt:message key="product.price"/>
                = ${product.price}</a>
        </li>
    </c:forEach>
</ul>
<form method="get" action="/product-create">
    <button type="submit"><fmt:message key="add.product"/></button>
</form>
<form method="get" action="/offer">
    <button type="submit" name="toOffers" value="offers"><fmt:message key="your.offers"/></button>
</form>
</body>
</html>
