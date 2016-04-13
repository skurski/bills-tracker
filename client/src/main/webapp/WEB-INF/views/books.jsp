<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
</head>
<body>
<h1>Books</h1>
<c:forEach items="${books}" var="book">
    <ul>
        <li>${book.id}</li>
        <li>${book.title}</li>
        <li>${book.author}</li>
    </ul>
</c:forEach>
</body>
</html>