<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User orders page</title>
</head>
<body>
<h1>User orders</h1>
<c:forEach var="order" items="${orders}">
<table width="300" border="1">
    <tr>
        <th>Product id</th>
        <th>Product name</th>
        <th>Price</th>
    </tr>
    <br>
    <strong>Order № ${order.id}</strong>
    <c:forEach var="product" items="${order.products}">
        <tr>
            <td><c:out value="${product.id}"/></td>
            <td><c:out value="${product.name}"/></td>
            <td><c:out value="${product.price}"/></td>
        </tr>
    </c:forEach>
    </c:forEach>
</table>
<br>
<form action="${pageContext.request.contextPath}/orders/admin/order" target="_self">
    <button>Go to manage orders (only for admin)</button>
</form>
</body>
</html>
