<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 04.01.2023
  Time: 21:37
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
<h1><fmt:message key="offers.create"/></h1>
<c:if test="${not empty requestScope.changeTypes}">
    <form method="post" action="/offer">
        <label for="changeTypes"><fmt:message key="offers.changeType"/>
            <select id="changeTypes" name="changeType">
                <c:forEach var="changeType" items="${requestScope.changeTypes}">
                    <option value="${changeType.changeType}">${changeType.description}</option>
                </c:forEach>
            </select>
        </label>
        <button type="submit" name="toOffers" value="offers"><fmt:message key="button.submit"/></button>
    </form>
</c:if>

<c:if test="${not empty requestScope.userProducts}">
    <form action="/offer" method="post">
        <label><fmt:message key="offers.productsToChange"/>:</label><br>
        <c:forEach items="${requestScope.userProducts}" var="product">
            <input type="checkbox" name="products" value="${product.id}">
            <a href="/product?id=${product.id}">${product.brand} ${product.model}</a>
        </c:forEach><br>
        <c:if test="${not empty requestScope.withPayment}">
            <label><fmt:message key="offer.payment"/></label>
            <input name="payment" type="number">
        </c:if>
        <button type="submit" value="offers" name="toOffers"><fmt:message key="page.registration.send"/></button>
    </form>
</c:if>

</body>
</html>
