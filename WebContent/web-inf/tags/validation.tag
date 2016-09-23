<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@attribute name="isValid" required="true"%>
<%@attribute name="error" required="true"%>

<% if (!Boolean.valueOf(isValid)) { %>
	<div class="validation">
		<p>Errors exist in the form:</p>
		<ul>
		<%= error %>
		</ul>
	</div>
<% } %>
