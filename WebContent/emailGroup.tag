<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="number" required="false"%>
<%@attribute name="emailAddress" required="false"%>
<%@attribute name="emailType" required="false"%>
<div class="phone-group" id="phone-group-${number}">
	<div class="form-group">
    	<label for="phoneNumber_${number}">Phone Number:</label>
    	<input type="text" class="form-control" name="emailAddress_${number}" id="emailAddress_${number}" 
    	placeholder="Enter Phone Number" value="${emailAddress}"/>
   	</div>
   	<div class="form-group">
    	<label for="emailAddressType_${number}">Phone Type:</label>
    	<t:emailType emailType="${emailType}" emailName="emailAddressType_${number}"></t:emailType>
   	</div>
</div>