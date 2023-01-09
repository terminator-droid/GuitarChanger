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
        <li>
            <a href="/user-products?userId=${user.id}&offset=0">${user.username}, ${user.firstName}, ${user.lastName}, ${user.role}</a>
        </li>
    </c:forEach>
</ul>
<a>Delete user</a>
<form method="post" action="/users">
    <select name="userId">
        <c:forEach items="${requestScope.users}" var="user">
            <option value="${user.id}">${user.username}</option>
        </c:forEach>
    </select>
    <button type="submit">Delete</button>
    <c:if test="${not empty requestScope.deletedUser}">
        <div style="color: cadetblue">User ${requestScope.deletedUser.username} was deleted</div>
    </c:if>
</form>
</body>
</html>
