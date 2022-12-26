<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 18.12.2022
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Registration page</h1>
    <form action="/registration" method="post">
        <label for="firstName">First name:
            <input type="text" name="firstName" id="firstName">
        </label><br>
        <label for="lastName">Last name:
            <input type="text" name="lastName" id="lastName">
        </label><br>
        <label for="username">Username:
            <input type="text" name="username" id="username">
        </label><br>
        <label for="phoneNumber">Phone number:
            <input type="text" name="phoneNumber" id="phoneNumber">
        </label><br>
        <label for="password">Password:
            <input type="password" name="password" id="password">
        </label><br>
        <label for="address">Address:
            <input type="text" name="address" id="address">
        </label><br>
        <select name="role" id="role">Role:
            <c:forEach var="role" items="${requestScope.roles}">
                <option value="${role}">${role}</option>
            </c:forEach>
        </select>
        <button type="submit">Send</button>
    </form>
</body>
</html>
