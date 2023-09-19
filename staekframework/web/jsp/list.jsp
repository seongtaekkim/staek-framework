<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	List list = (List) request.getAttribute("users");
%>
<html>
<head>
<body>
<h1>유저목록</h1>
<p><a href='add'>유저목록</a></p>
<jsp:useBean id="users" scope="request" type="java.util.List"/>
<c:forEach var="user" items="${users}">
	${user.id},
	${user.name},
	${user.password},
</c:forEach>
<% for (int i=0 ; i<list.size() ; i++) { %>
	<%=list.get(i).toString()%>
<% }%>
</body>
</head>
</html>
