<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="number" required="false"%>
<%@attribute name="phoneNumber" required="false"%>
<%@attribute name="phoneType" required="false"%>
<%@attribute name="phoneId" required="true" %>
<%@attribute name="className" required="false"%>

<div class="phone-group two-col-del ${className}" id="phone-group-${number}">
	<span class="btn btn-small cancel deletePhone" id="${number}" >X</span>
	<div class="form-group">
    	<label for="phoneNumber_${number}">Phone Number:</label>
    	<input type="text" class="form-control" name="phoneNumber_${number}" id="phoneNumber_${number}" 
    	placeholder="Enter Phone Number" value="${phoneNumber}"/>
   	</div>
   	<div class="form-group">
    	<label for="phoneNumberType_1">Phone Type:</label>
    	<t:phoneType phoneType="${phoneType}" phoneName="phoneNumberType_${number}"></t:phoneType>
   	</div>
</div>
<input class="hidden" id="phoneNumberId_${number}" name="phoneNumberId_${number}" value="<%=phoneId %>" />
<div class="push"></div>