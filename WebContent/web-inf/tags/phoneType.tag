<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@attribute name="phoneType" required="false"%>
<%@attribute name="phoneName" required="false"%>
<select class="form-control" name="${phoneName}"> 
	<option value="" <% if (phoneType.length() < 2) { %>selected="selected" <% } %>> - Phone Type - </option> 
	<option value="Home" <% if (phoneType.equals("Home")) { %>selected="selected" <% } %>>Home</option> 
	<option value="Work" <% if (phoneType.equals("Work")) { %>selected="selected" <% } %>>Work</option> 
	<option value="Other" <% if (phoneType.equals("Other")) { %>selected="selected" <% } %>>Other</option> 
</select>