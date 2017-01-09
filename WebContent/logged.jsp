<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
	<p>
		Welcome,
		<%=session.getAttribute("firstName")%>
		<br/>
		Your last name is: <%=session.getAttribute("lastName") %>
		<br/>
		Your user name is: <%=session.getAttribute("username") %></p>
	<p>
		<a href="index.jsp">back to index.jsp</a>
</body>
</html>