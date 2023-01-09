<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 17.12.2022
  Time: 19:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="background-color: lavender">
<%--    <%@include file="header.jsp"%>--%>
    <img width="200" height="100" src="${pageContext.request.contextPath}/images/tester.jpg" alt="alter">
<%--    <c:choose>--%>
<%--        <c:when test="${not empty requestScope.user}">--%>
<%--            <h1>--%>
<%--                <fmt:message key="page.products.username"/> ${requestScope.user.username}--%>
<%--            </h1>--%>
<%--        </c:when>--%>
<%--        <c:otherwise>--%>
<%--            <h1>--%>
<%--                <fmt:message key="page.products.available"/>:--%>
<%--            </h1>--%>
<%--        </c:otherwise>--%>
<%--    </c:choose>--%>
<%--    <ul>--%>
<%--        <c:forEach var="product" items="${requestScope.products}">--%>
<%--        <li>--%>
<%--            <a href="/product?id=${product.id}"><fmt:message key="product.brand"/> - ${product.brand}, <fmt:message key="product.model"/> - ${product.model}, <fmt:message key="product.price"/> = ${product.price}</a>--%>
<%--        </li>--%>
<%--        </c:forEach>--%>
<%--    </ul>--%>
</body>
</html>
