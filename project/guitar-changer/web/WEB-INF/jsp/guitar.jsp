<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 18.12.2022
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>
    Additional guitar info
</h1>
<ul>
    <li>
        Year - ${requestScope.product.year}
    </li>
    <li>
        Pick-ups - ${requestScope.product.pickUps}
    </li>
    <li>
        Fingerboard wood - ${requestScope.product.wood}
    </li>
    <li>
        Change type - ${requestScope.product.changeType}
    </li>
    <li>
        Change wish - ${requestScope.product.changeWish}
    </li>
    <li>
        Change value - ${requestScope.product.changeValue}
    </li>
</ul>
</body>
</html>
