<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Book Profile</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
</head>
<body>
<h1>Book Profile</h1>
<ul>
    <li>${book.id}</li>
    <li>${book.title}</li>
    <li>${book.author}</li>
</ul>
</body>
</html>