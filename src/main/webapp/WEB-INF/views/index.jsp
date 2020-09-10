<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<h1>Hello to casual cat shop!</h1>
<form action="${pageContext.request.contextPath}/inject/data" target="_self">
    <button>Inject test data into DB</button>
</form>
<h2>User menu</h2>
<a href="${pageContext.request.contextPath}users/registration">Sign up</a><br>
<a href="${pageContext.request.contextPath}/products/all">All products</a><br>
<a href="${pageContext.request.contextPath}/shopping-carts/products/all">Shopping cart</a><br>
<a href="${pageContext.request.contextPath}/orders/user/order">Orders</a><br>

<h3>Admin menu</h3>
<a href="${pageContext.request.contextPath}/users/all">All users</a><br>
<a href="${pageContext.request.contextPath}/products/add">Create a new product</a><br>
<a href="${pageContext.request.contextPath}/products/admin/all">Manage products</a><br>
<a href="${pageContext.request.contextPath}/orders/admin/order">Manage orders</a><br>

</body>
</html>
