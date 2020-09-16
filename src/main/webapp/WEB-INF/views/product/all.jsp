<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products page</title>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
</head>
<body>
<h1>All products</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Add</th>
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
                <form action="${pageContext.request.contextPath}/shopping-carts/products/add" method="get">
                    <input type="hidden" name="id" value="${product.id}">
                    <button type="submit">Add to shopping cart</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/shopping-carts/products" target="_self">
    <button>Go to shopping cart</button>
</form>
</body>
</html>
