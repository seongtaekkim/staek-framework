<%@ page contentType="text/html; charset=UTF-8"%>
<%
	String findStr = request.getParameter("findStr");
	String exeWriteResult = (String) request.getAttribute("exeWriteResult");
%>
<html>
<head>
<body>
<pre>

list.jsp

findStr : <%=findStr%>

exeWriteResult : <%=exeWriteResult%>

</pre>
</body>
</head>
</html>
