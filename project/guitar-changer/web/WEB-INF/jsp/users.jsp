<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 18.12.2022
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Users:</h1>
    <ul>
        <c:forEach items="${requestScope.users}" var="user">
            <li> ${user.username}, ${user.firstName}, ${user.lastName}, ${user.role}</li>
        </c:forEach>
    </ul>
</body>
</html>
