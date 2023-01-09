<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 18.12.2022
  Time: 20:25
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
<%@include file="header.jsp"%>
<h1><fmt:message key="page.pedal.header"/></h1>
<ul>
    <li>
        <fmt:message key="product.brand"/> - ${requestScope.product.brand}
    </li>
    <li>
        <fmt:message key="product.model"/> - ${requestScope.product.model}
    </li>
    <li>
        <fmt:message key="product.description"/> - ${requestScope.product.description}
    </li>
    <li>
        <fmt:message key="product.changeType"/> - ${requestScope.product.changeType}
    </li>
    <li>
        <fmt:message key="product.changeWish"/> - ${requestScope.product.changeWish}
    </li>
    <li>
        <fmt:message key="product.changeValue"/> - ${requestScope.product.changeValue}
    </li>
</ul>
<c:if test="${requestScope.product.closed == true}">
    <h2 style="color: red"><fmt:message key="product.closed"/> </h2>
</c:if>
<c:if test="${requestScope.product.userId != sessionScope.user.id}">
    <form method="post" action="/offer">
        <button type="submit" name="interchange" value="${requestScope.product.id}"><fmt:message key="offer.makeOffer"/> </button>
    </form>
</c:if>
<c:if test="${requestScope.product.userId == sessionScope.user.id}">

    <c:if test="${not empty requestScope.suitableProducts}">
        <h2><fmt:message key="product.suitableProducts"/> </h2>
        <ul>
            <c:forEach items="${requestScope.suitableProducts}" var="product">
                <li>
                    <a href="/product?id=${product.product.id}"><fmt:message key="product.brand"/>  - ${product.product.brand.name}, <fmt:message key="product.model"/>  - ${product.product.model}, <fmt:message key="product.price"/>  = ${product.product.price}</a><br>
                    <span><fmt:message key="product.delta"/> ${product.payment}</span>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${not empty requestScope.closingOffer}">
        <h3><fmt:message key="product.close.byOffer"/> :</h3>
        <a href="/offer?offerId=${requestScope.closingOffer.id}"><fmt:message key="page.users.user"/>  ${requestScope.closingOffer.buyerName}
            offers ${requestScope.closingOffer.changeType}</a>
    </c:if>
    <c:if test="${empty requestScope.closingOffer }">
        <c:if test="${not empty requestScope.offers}">
            <h2><fmt:message key="product.offers"/> :</h2>
            <ul>
                <c:forEach var="offer" items="${requestScope.offers}">
                    <li>
                        <a href="/offer?offerId=${offer.id}"><fmt:message key="page.users.user"/>  ${offer.buyerName} offers ${offer.changeType}</a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty requestScope.offers}">
            <h3><fmt:message key="product.noOffers"/> </h3>
        </c:if>
    </c:if>
</c:if>
</body>
</html>
