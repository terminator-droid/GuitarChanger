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
<%--<%@include file="header.jsp" %>--%>
<%--<h1>--%>
<%--    Additional guitar info--%>
<%--</h1>--%>
<%--<img width="200" height="100" src="https://upload.wikimedia.org/wikipedia/commons/4/45/GuitareClassique5.png" alt="photo">--%>
<img src="${pageContext.request.contextPath}/images/registration_guitar.png" alt="input">

<%--<ul>--%>
<%--    <li>--%>
<%--        Brand - ${requestScope.product.brand}--%>
<%--    </li>--%>
<%--    <li>--%>
<%--        Model - ${requestScope.product.model}--%>
<%--    </li>--%>
<%--    <li>--%>
<%--        Year - ${requestScope.product.year}--%>
<%--    </li>--%>
<%--    <li>--%>
<%--        Pick-ups - ${requestScope.product.pickUps}--%>
<%--    </li>--%>
<%--    <li>--%>
<%--        Fingerboard wood - ${requestScope.product.wood}--%>
<%--    </li>--%>
<%--    <li>--%>
<%--        Change type - ${requestScope.product.changeType}--%>
<%--    </li>--%>
<%--    <li>--%>
<%--        Change wish - ${requestScope.product.changeWish}--%>
<%--    </li>--%>
<%--    <li>--%>
<%--        Change value - ${requestScope.product.changeValue}--%>
<%--    </li>--%>
<%--    <li>--%>
<%--        Price - ${requestScope.product.price}--%>
<%--    </li>--%>
<%--    <li>--%>
<%--        Description - ${requestScope.product.description}--%>
<%--    </li>--%>
<%--</ul>--%>
<%--<br>--%>
<%--<c:if test="${requestScope.product.closed == true}">--%>
<%--    <h2 style="color: red">This product is closed</h2>--%>
<%--</c:if>--%>
<%--<c:if test="${requestScope.product.userId != sessionScope.user.id}">--%>
<%--    <form method="post" action="/offer">--%>
<%--        <button type="submit" name="interchange" value="${requestScope.product.id}">Make offer</button>--%>
<%--    </form>--%>
<%--</c:if>--%>
<%--<c:if test="${requestScope.product.userId == sessionScope.user.id}">--%>

<%--    <c:if test="${not empty requestScope.suitableProducts}">--%>
<%--        <h2>Suitable products:</h2>--%>
<%--        <ul>--%>
<%--            <c:forEach items="${requestScope.suitableProducts}" var="product">--%>
<%--                <li>--%>
<%--                    <a href="/product?id=${product.product.id}">Brand - ${product.product.brand.name}, model - ${product.product.model}, price = ${product.product.price}</a><br>--%>
<%--                    <span>Price delta ${product.payment}</span>--%>
<%--                </li>--%>
<%--            </c:forEach>--%>
<%--        </ul>--%>
<%--    </c:if>--%>

<%--    <c:if test="${not empty requestScope.closingOffer}">--%>
<%--        <h3>This product was closed by offer:</h3>--%>
<%--        <a href="/offer?offerId=${requestScope.closingOffer.id}">User ${requestScope.closingOffer.buyerName}--%>
<%--            offers ${requestScope.closingOffer.changeType}</a>--%>
<%--    </c:if><br>--%>
<%--    <c:if test="${empty requestScope.closingOffer }">--%>
<%--        <c:if test="${not empty requestScope.offers}">--%>
<%--            <h2>Offers:</h2>--%>
<%--            <ul>--%>
<%--                <c:forEach var="offer" items="${requestScope.offers}">--%>
<%--                    <li>--%>
<%--                        <a href="/offer?offerId=${offer.id}">User ${offer.buyerName} offers ${offer.changeType}</a>--%>
<%--                    </li>--%>
<%--                </c:forEach>--%>
<%--            </ul>--%>
<%--        </c:if><br>--%>
<%--        <c:if test="${empty requestScope.offers}">--%>
<%--            <h3>This product don't have any offers</h3>--%>
<%--        </c:if>--%>
<%--    </c:if><br>--%>
<%--    <form method="post" action="/product">--%>
<%--        <button type="submit" name="deleteProduct" value="${requestScope.product.id}">Delete product</button>--%>
<%--    </form>--%>
<%--</c:if>--%>
</body>
</html>
