<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 26.12.2022
  Time: 20:48
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

<fmt:setLocale value="${sessionScope.lang != null ? sessionScope.lang : (param.lang != null ? param.lang : 'en_US')}"/>
<fmt:setBundle basename="translations"/>

<div id="locale">
    <form style="text-align: right" action="/locale" method="post">
        <button type="submit" name="lang" value="en_US">EN</button>
        <button type="submit" name="lang" value="ru_RU">RU</button>
    </form>
</div>
<div class="ui-button">
    <c:if test="${not empty sessionScope.user}">
        <form style="text-align: right" action="/logout" method="post">
            <button type="submit" class="ui-button" style="width: 120px"><fmt:message key="logout"/></button>
        </form>
        <form style="text-align: right" action="/account" method="get">
            <button type="submit" class="ui-button" style="width: 120px"><fmt:message key="account"/></button>
        </form>
        <form style="text-align: right" action="/products" method="get" >
            <button type="submit" class="ui-button" style="width: 120px"><fmt:message key="main"/></button>
        </form>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <form style="text-align: right" action="/users" method="get" >
                <button type="submit" class="ui-button" style="width: 120px"><fmt:message key="page.users.users"/></button>
            </form>
        </c:if>
    </c:if>
</div>
</body>
</html>
