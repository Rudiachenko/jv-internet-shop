<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<h1>Hello world!</h1>
<form action="${pageContext.request.contextPath}/inject/data" target="_self">
    <button>Inject test data into DB</button>
</form>
<a href="${pageContext.request.contextPath}users/registration">Sign up</a><br>
<a href="${pageContext.request.contextPath}/users/all">All users</a><br>
<a href="${pageContext.request.contextPath}/products/all">All products</a><br>
<a href="${pageContext.request.contextPath}/products/add">Add products</a><br>
</body>
</html>
