<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product page</title>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/products/add">
    Product: <input type="text" name="product">
    Price: <input type="text" name="price">
    <button type="submit">Add product</button>
</form>
</body>
</html>
