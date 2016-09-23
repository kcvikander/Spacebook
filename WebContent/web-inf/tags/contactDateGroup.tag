<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="number" required="false"%>
<%@attribute name="contactDate" required="false"%>
<%@attribute name="contactDateType" required="false"%>
<%@attribute name="dateId" required="false"%>
<%@attribute name="className" required="false"%>

<div class="contact-date-group two-col-del ${className}" id="contact-date-group-${number}">
	<span class="btn btn-small cancel deleteDate" id="${number}" >X</span>
	<div class="form-group">
    	<label for="contactDate_${number}">Important Date:</label>
    	<input type="date" class="form-control" name="contactDate_${number}" id="contactDate_${number}" 
    		 value="${contactDate}"/>
   	</div>
   	<div class="form-group">
    	<label for="contactDateType_1">Date Type:</label>
    	<t:contactDateType dateType="${contactDateType}" dateName="contactDateType_${number}"></t:contactDateType>
   	</div>
</div>
<input class="hidden" id="contactDateId_${number}" name="contactDateId_${number}" value="<%=dateId %>"/>
<div class="push"></div>