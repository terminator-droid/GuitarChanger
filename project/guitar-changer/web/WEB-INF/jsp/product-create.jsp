<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 07.01.2023
  Time: 12:32
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
<h1><fmt:message key="page.product-create.create"/></h1>
<c:if test="${not empty requestScope.categories}">
    <form action="/product-create" method="get">
        <label for="category"><fmt:message key="page.product-create.category"/>
            <select id="category" name="category">
                <c:forEach items="${requestScope.categories}" var="category">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
        </label><br>
        <button type="submit" style="width: 120px" ><fmt:message key="page.product-create.confirm"/></button>
    </form>
</c:if>

<c:if test="${applicationScope.category.name == 'guitar'}">
    <form method="post" action="/product-create" enctype="multipart/form-data">
        <label for="brand">Brand:
            <select name="brand" id="brand" required>
                <c:forEach var="brand" items="${requestScope.brands}">
                    <option value="${brand.id}">${brand.name}</option>
                </c:forEach>
            </select>
        </label><br>
        <label for="model"><fmt:message key="product.model"/>:
            <input type="text" id="model" name="model" required>
        </label><br>
        <label for="country"><fmt:message key="product.country"/>:
            <input type="text" name="country" id="country">
        </label><br>
        <label for="year"><fmt:message key="product.year"/>:
            <input type="number" name="year" id="year">
        </label><br>
        <label for="description"><fmt:message key="product.description"/>:
            <input type="text" name="description" id="description" required>
        </label><br>
        <label for="wood"><fmt:message key="product.wood"/>:
            <input type="text" name="wood" id="wood">
        </label><br>
        <label for="pickUps"><fmt:message key="product.pickUps"/>:
            <input type="text" name="pickUps" id="pickUps">
        </label><br>
        <label for="price"><fmt:message key="product.price"/>:
            <input type="number" name="price" id="price" required>
        </label><br>
        <label for="changeWish"><fmt:message key="product.changeWish"/>:
            <input type="text" name="changeWish" id="changeWish">
        </label><br>
        <label for="changeType"><fmt:message key="product.changeType"/>:
            <select name="changeType" id="changeType" required>
                <c:forEach var="changeType" items="${requestScope.changeTypes}">
                    <option value="${changeType.changeType}">${changeType.description}</option>
                </c:forEach>
            </select>
        </label><br>
        <label for="changeValue"><fmt:message key="product.changeValue"/>:
            <input type="number" name="changeValue" id="changeValue">
        </label><br>
        <label for="image"><fmt:message key="product.image"/>:
            <input type="file" id="image" name="image" required>
        </label><br>

        <button type="submit" style="width: 120px" value="guitar" name="product"><fmt:message key="page.product-create.save"/></button>
    </form>
</c:if>

</body>
</html>
