<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 17.12.2022
  Time: 19:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>
        Available products:
    </h1>
    <ul>
        <c:forEach var="product" items="${requestScope.products}">
        <li>
            <a href="/product?id=${product.id}">Brand - ${product.brand}, model - ${product.model}, price = ${product.price}</a>
        </li>
        </c:forEach>
    </ul>
</body>
</html>
