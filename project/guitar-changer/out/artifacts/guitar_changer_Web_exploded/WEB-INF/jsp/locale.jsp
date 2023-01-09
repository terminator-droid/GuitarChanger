<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 09.01.2023
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>Title</title>
</head>
<body style="background-color: lavender">

<fmt:setLocale value="${sessionScope.lang != null ? sessionScope.lang : (param.lang != null ? param.lang : 'en_US')}"/>
<fmt:setBundle basename="translations"/>

<div id="locale">
  <form style="text-align: right" action="/locale" method="post">
    <button type="submit" name="lang" value="en_US">EN</button>
    <button type="submit" name="lang" value="ru_RU">RU</button>
  </form>
</div>
</body>
</html>
