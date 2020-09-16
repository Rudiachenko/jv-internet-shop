<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage orders page</title>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
</head>
<body>
<h1>Manage orders</h1>
<c:forEach var="order" items="${orders}">
<table width="300" border="1">
    <tr>
        <th>User id</th>
        <th>Product id</th>
        <th>Product name</th>
        <th>Price</th>
    </tr>
    <br>
    <form action="${pageContext.request.contextPath}/orders/admin/delete" method="get">
        <input type="hidden" name="id" value="${order.id}">
        <button type="submit">Delete order № ${order.id}</button>
    </form>
    <c:forEach var="product" items="${order.products}">
        <tr>
            <td><c:out value="${order.userId}"/></td>
            <td><c:out value="${product.id}"/></td>
            <td><c:out value="${product.name}"/></td>
            <td><c:out value="${product.price}"/></td>
        </tr>
    </c:forEach>
    </c:forEach>
</table>
<br>
</body>
</html>
