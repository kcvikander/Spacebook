<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@attribute name="id" required="true"%>
<%@attribute name="label" required="true"%>
<%@attribute name="type" required="true"%>

<div class="form-element">
	<label for="${id}">${label}</label>
	<jsp:doBody/>
</div>


