<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 14.09.2020
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/login">
    Please provide your login: <input type="text" name="login">
    Please provide your password: <input type="password" name="psw">
    <button type="submit">Login</button>
</form>
</body>
</html>
