<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<div class="step-tracker">
	<% int step = Integer.parseInt(request.getSession().getAttribute("step").toString()); %>
	<div class="step active"></div>
	<div class="step <% if (step == 2){ %>active<% } %>"></div>
	<div class="step <% if (step == 3){ %>active<% } %>"></div>
	
</div>