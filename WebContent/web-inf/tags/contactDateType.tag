<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@attribute name="dateType" required="false"%>
<%@attribute name="dateName" required="false"%>
<select class="form-control" name="${dateName}"> 
	<option value="" <% if (dateType.length() < 2) { %>selected="selected" <% } %>> - Date Type - </option> 
	<option value="Birthday" <% if (dateType.equals("Birthday")) { %>selected="selected" <% } %>>Birthday</option> 
	<option value="Anniversary" <% if (dateType.equals("Anniversary")) { %>selected="selected" <% } %>>Anniversary</option> 
	<option value="Other" <% if (dateType.equals("Other")) { %>selected="selected" <% } %>>Other</option> 
</select>