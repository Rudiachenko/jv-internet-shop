<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products in shopping cart page</title>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
</head>
<body>
<h1>All products in shopping cart</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/shopping-carts/products/delete" method="get">
                    <input type="hidden" name="id" value="${product.id}">
                    <button type="submit">Delete from shopping cart</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/products/all" target="_self">
    <button>Go to all products list</button>
</form>
<form action="${pageContext.request.contextPath}/orders/complete-order" target="_self">
    <button>Checkout</button>
</form>
</body>
</html>
