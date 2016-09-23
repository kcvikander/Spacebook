<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.cvikander.model.User" 
    %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
	User user = (User)session.getAttribute("user");
%>
<t:layout title="SpaceBook Signup - Finished">
<div class="main">
	<form action="SpaceBook" method="post">
		<input class="hidden" type="text" name="step" value="4" />
		<h2>All Done!</h2>
		<section>
			<h3>Your account has been created!</h3>
			<p>Details:</p>
			<t:formElement label="First Name" type="input" id=""><input type="text" value="${user.getFirstName()}" disabled/></t:formElement>
			<t:formElement label="Last Name" type="input" id=""><input type="text" value="${user.getLastName()}" disabled/></t:formElement>
			<t:formElement label="Street" type="input" id=""><input type="text" value="${user.getStreet()}" disabled/></t:formElement>
			<t:formElement label="City" type="input" id=""><input type="text" value="${user.getCity()}" disabled/></t:formElement>
			<t:formElement label="State" type="input" id=""><input type="text" value="${user.getState()}" disabled/></t:formElement>
			<t:formElement label="City" type="input" id=""><input type="text" value="${user.getCity()}" disabled/></t:formElement>
			<t:formElement label="Zip Code" type="input" id=""><input type="text" value="${user.getZipCode()}" disabled/></t:formElement>
			<t:formElement label="User Name" type="input" id=""><input type="text" value="${user.getUserName()}" disabled/></t:formElement>
		</section>
	</form>
</div>
</t:layout>