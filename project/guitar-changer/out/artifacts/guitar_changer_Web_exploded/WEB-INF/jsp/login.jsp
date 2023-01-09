<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 26.12.2022
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<%@include file="locale.jsp"%>
<body style="background-color: lavender">

<img width="200" height="100" src="${pageContext.request.contextPath}/images/tester.jpg" alt="User image">
  <form action="/login" method="post">
      <label for="username"><fmt:message key="page.login.username"/>:
        <input name="username" type="text" id="username" value="${applicationScope.username}">
      </label><br>
      <label for="password"><fmt:message key="page.login.password"/>:
        <input name="password" type="text" id="password">
      </label><br>
    <button type="submit"><fmt:message key="page.login.button.login"/></button>
    <a href="/registration">
      <button type="button"><fmt:message key="page.login.button.register"/></button>
    </a>
      <c:if test="${applicationScope.error != null}">
          <div style="color: red">
              <span><fmt:message key="page.login.error"/></span>
          </div>
      </c:if>
  </form>
</body>
</html>
