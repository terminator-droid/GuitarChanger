<%--
  Created by IntelliJ IDEA.
  User: ussda
  Date: 18.12.2022
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/administration" method="post">
      <label for="username">Username:
          <input type="text" name="username" id="username">
      </label>
      <label for="password">Password:
          <input type="password" name="password" id="password">
      </label>
      <button type="submit">Send</button>
    </form>
</body>
</html>
