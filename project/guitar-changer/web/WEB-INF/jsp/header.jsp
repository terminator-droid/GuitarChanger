<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 26.12.2022
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <c:if test="${not empty sessionScope.user}">
        <form action="/logout" method="post">
            <button type="submit">Logout</button>
        </form>
    </c:if>
</div>
</body>
</html>
