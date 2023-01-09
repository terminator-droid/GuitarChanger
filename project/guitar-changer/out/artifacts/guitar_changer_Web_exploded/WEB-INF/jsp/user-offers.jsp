<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 05.01.2023
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><fmt:message key="page.users-offers.alloffers"/></title>
</head>
<body style="background-color: lavender">
<%@include file="header.jsp"%>
<c:if test="${not empty requestScope.offers}">
    <h1><fmt:message key="page.users-offers.offers"/> ${sessionScope.user.username}</h1>
    <ul>
        <c:forEach var="offer" items="${requestScope.offers}">
            <li>
                <a href="/offer?offerId=${offer.id}">${offer.interchangeBrand} ${offer.interchangeModel } for ${offer.changeType}</a>
            </li>
        </c:forEach>
    </ul>
</c:if>
<c:if test="${empty requestScope.offers}">
    <h1><fmt:message key="page.users-offers.nooffers"/></h1>
</c:if>

</body>
</html>
