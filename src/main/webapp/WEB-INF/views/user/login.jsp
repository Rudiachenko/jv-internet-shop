<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/login">
    <h4 style="color: red">${message}</h4>
    Please provide your login: <input type="text" name="login">
    Please provide your password: <input type="password" name="psw">
    <button type="submit">Login</button>
</form>
<br>
<form action="${pageContext.request.contextPath}/registration">
    <button type="submit">registration</button>
</form>

</body>
</html>
