<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@attribute name="emailType" required="false"%>
<%@attribute name="emailName" required="false"%>
<select class="form-control" name="${emailName}"> 
	<option value="" <% if (emailType.length() < 2) { %>selected="selected" <% } %>> - Email Type - </option> 
	<option value="Personal" <% if (emailType.equals("Personal")) { %>selected="selected" <% } %>>Personal</option> 
	<option value="Work" <% if (emailType.equals("Work")) { %>selected="selected" <% } %>>Work</option> 
	<option value="Other" <% if (emailType.equals("Other")) { %>selected="selected" <% } %>>Other</option> 
</select>