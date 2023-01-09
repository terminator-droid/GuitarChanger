<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 04.01.2023
  Time: 18:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Offer</title>
</head>
<%@include file="header.jsp" %>
<body style="background-color: lavender">
<h1>
    <fmt:message key="offer.header"/>
</h1>

<c:if test="${requestScope.offer.buyer == sessionScope.user.id}">
    <h3>You offer ${requestScope.offer.changeType} for product:
        <a href="/product?id=${requestScope.interchange.id}"><fmt:message key="product.brand"/> - ${requestScope.interchange.brand}, <fmt:message key="product.model"/>
            - ${requestScope.interchange.model}, <fmt:message key="product.price"/> - ${requestScope.interchange.price}</a>
    </h3>
    <c:if test="${not empty requestScope.offer.changeValue}">
        <h3><fmt:message key="product.changeValue"/> - ${requestScope.offer.changeValue}</h3>
    </c:if>
    <c:if test="${not empty requestScope.offerProducts}">
        <h3><fmt:message key="offer.offerdProducts"/>:</h3>
        <ul>
            <c:forEach items="${requestScope.offerProducts}" var="product">
                <li>
                    <a href="/product?id=${product.id}"><fmt:message key="product.brand"/> - ${product.brand}, <fmt:message key="product.model"/> - ${product.model}, <fmt:message key="product.price"/>
                        = ${product.price}</a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${requestScope.paymentDelta < 0}">
        <h3 style="color: red"><fmt:message key="seller.owe.you"/> ${requestScope.paymentDelta * -1}</h3>
    </c:if>
    <c:if test="${requestScope.paymentDelta > 0}">
        <h3 style="color: aquamarine"><fmt:message key="you.owe.seller"/> ${requestScope.paymentDelta}</h3>
    </c:if>
    <c:if test="${not empty requestScope.accepted}">
        <h2 style="color: cadetblue">This offer was accepted</h2>
        <button name="chat" value="chat"><fmt:message key="seller.chat"/></button>
    </c:if>
    <c:if test="${empty requestScope.accepted}">
        <form action="/offer" method="post">
            <button type="submit" name="deleteOffer" value="${requestScope.offer.id}"><fmt:message key="offer.delete"/></button>
        </form>
    </c:if>
</c:if>

<c:if test="${requestScope.offer.buyer != sessionScope.user.id}">
    <h3><fmt:message key="page.users.user"/> - ${requestScope.offer.buyerName}</h3>
    <h3><fmt:message key="product.changeType"/> - ${requestScope.offer.changeType}</h3>
    <c:if test="${not empty requestScope.offer.changeValue}">
        <h3><fmt:message key="product.changeValue"/> - ${requestScope.offer.changeValue}</h3>
    </c:if>
    <c:if test="${not empty requestScope.offerProducts}">
        <h3><fmt:message key="offer.offerdProducts"/>:</h3>
        <ul>
            <c:forEach items="${requestScope.offerProducts}" var="product">
                <li>
                    <a href="/product?id=${product.id}"><fmt:message key="product.brand"/> - ${product.brand}, <fmt:message key="product.model"/> - ${product.model}, <fmt:message key="product.price"/>
                        = ${product.price}</a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${requestScope.paymentDelta < 0}">
        <h3 style="color: aquamarine"><fmt:message key="you.owe.buyer"/> ${requestScope.paymentDelta * -1}</h3>
    </c:if>
    <c:if test="${requestScope.paymentDelta > 0}">
        <h3 style="color: red"><fmt:message key="buyer.owes.you"/> ${requestScope.paymentDelta}</h3>
    </c:if>

    <c:if test="${not empty requestScope.accepted}">
        <h2 style="color: cadetblue">You accepted this offer</h2>
        <button name="chat" value="chat"><fmt:message key="buyer.chat"/></button>
    </c:if>


    <c:if test="${empty requestScope.accepted}">
        <form action="/offer" method="post">
            <button type="submit" name="offer" value="${requestScope.offer.id}"><fmt:message key="offer.accept"/></button>
        </form>
    </c:if>

</c:if>
</body>
</html>
