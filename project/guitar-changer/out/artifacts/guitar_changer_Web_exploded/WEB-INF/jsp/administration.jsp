<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 18.12.2022
  Time: 21:29
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
    <form action="/administration" method="post">
      <label for="username"><fmt:message key="page.login.username"/>:
          <input type="text" name="username" id="username" value="${requestScope.username}">
      </label>
      <label for="password"><fmt:message key="page.registration.password"/>:
          <input type="password" name="password" id="password">
      </label>
      <button type="submit"><fmt:message key="page.registration.send"/></button>
        <c:if test="${requestScope.error != null}">
            <div style="color: red">
                <span>${requestScope.error}</span>
            </div>
        </c:if>
    </form>
</body>
</html>
