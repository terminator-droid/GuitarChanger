<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 18.12.2022
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="background-color: lavender">
  <h1>${requestScope.error}</h1>
<c:if test="${not empty requestScope.prevPage}">
    <form method="get">
        <a href="${requestScope.prevPage}">
            <button type="button">Go back</button>
        </a>
    </form>
</c:if>
</body>
</html>
