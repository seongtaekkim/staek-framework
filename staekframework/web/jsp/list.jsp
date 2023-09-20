<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<body>
<h1>유저목록</h1>
<p><a href='#'>유저목록</a></p>
<c:forEach var="user" items="${users}">
	<p>${user.id},${user.name},${user.password}</p>
</c:forEach>
</body>
</head>
</html>