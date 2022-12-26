<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 26.12.2022
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <form action="/login" method="post">
      <label for="username">Username:
        <input name="username" type="text" id="username" value="${applicationScope.username}">
      </label><br>
      <label for="password">Password:
        <input name="username" type="text" id="password">
      </label><br>
    <button type="submit">Login</button>
    <a href="/registration">
      <button type="button">Register</button>
    </a>
      <c:if test="${applicationScope.error != null}">
          <div style="color: red">
              <span>Incorrect username or password</span>
          </div>
      </c:if>
  </form>
</body>
</html>
