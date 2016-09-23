<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="number" required="false"%>
<%@attribute name="emailAddress" required="false"%>
<%@attribute name="emailType" required="false"%>
<%@attribute name="emailId" required="false"%>
<%@attribute name="className" required="false"%>

<div class="email-group ${className} two-col-del" id="email-group-${number}">
	<div class="form-group">
    	<label for="emailAddress_${number}">Email Address:</label>
    	<input type="text" class="form-control" name="emailAddress_${number}" id="emailAddress_${number}" 
    	placeholder="Enter Email Address" value="${emailAddress}"/>
   	</div>
   	<div class="form-group">
    	<label for="emailAddressType_1">Email Type:</label>
    	<t:emailType emailType="${emailType}" emailName="emailAddressType_${number}"></t:emailType>
   	</div>
   	<span class="btn btn-small cancel deleteEmail" id="${Integer.toString(number)}" >X</span>
</div>
<input class="hidden" id="emailAddressId_${number}" name="emailAddressId_${number}" value="<%=emailId %>"/>
<div class="push"></div>