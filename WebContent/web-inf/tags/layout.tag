<%@ tag language="java" pageEncoding="ISO-8859-1" description="Main layout, header and footer" import="com.cvikander.model.User"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title" required="true"%>
<%
	User user = (User)session.getAttribute("user");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${title}</title>
<t:resources></t:resources>
</head>
<body>
<div class="container">
	<div class="header">
		<h1>SpaceBook</h1>
		<p class="slug">
			The number one social network for aliens!
		</p>
		<%
			if (user != null){
		%>
			<div class="user-info">Hello, <% user.getFirstName(); %>. </div>
			<div class="user-actions"><a id="logout">Logout</a></div>
		<% } %>
	</div>
