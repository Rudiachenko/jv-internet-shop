<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
<h1>Hello! Please provide your user details</h1>

<form method="post" action="${pageContext.request.contextPath}/registration">
    <h4 style="color: red">${message}</h4>
    Please provide your login: <input type="text" name="login">
    Please provide your password: <input type="password" name="psw">
    Please repeat your password: <input type="password" name="psw-rpt">
    <button type="submit">Register</button>
</form>
</body>
</html>
