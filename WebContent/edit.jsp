<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.cvikander.model.User, 
    			com.cvikander.model.ContactList, 
    			com.cvikander.model.Contact, 
    			com.cvikander.model.Address,
    			com.cvikander.model.Email,
    			com.cvikander.model.Phone,
    			com.cvikander.model.ContactDate,
    			com.cvikander.service.ContactService,
    			java.util.List" 
    %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
	User user = (User)session.getAttribute("user");
	Contact contact = (Contact)request.getAttribute("contact");
	String editLabel = "";
	String idString = "";
	String stateStr = "";
	String fnStr = "";
	String lnStr = "";
	String strStr = ""; 
	String cityStr = ""; 
	String poStr = "";
	String addressIdString = "";
	int numEmails, numPhones, numDates, i;
	if (contact.getContactId() > 0){
		idString = Integer.toString(contact.getContactId());
		addressIdString = Integer.toString(contact.getAddress().getId());
		editLabel = "Edit";
		numEmails = contact.getEmails().size();
		numPhones = contact.getPhoneNumbers().size();
		numDates = contact.getContactDates().size();
		stateStr = contact.getAddress().getState();
		fnStr = contact.getFirstName();
		lnStr = contact.getLastName();
		cityStr = contact.getAddress().getCity();
		poStr = contact.getAddress().getPostalCode();
		strStr = contact.getAddress().getStreet();
	} else {
		idString = Integer.toString(0);
		editLabel = "Create";
		numEmails = 0;
		numPhones = 0;
		numDates = 0;
		addressIdString = "0";
	}
	
%>
<t:header title="Spacebook - Add/Edit Contact"></t:header>
<div class="main">
	<div id="errorContainer" style="display:none;">
		<h3>There are errors below:</h3>
		<div id="errorMessage"></div>
	</div>
	<div class="contact-edit">
	<form action="SaveContact" method="post" id="saveContactForm">
		<div class="left">
			<h2><%= editLabel %> Contact</h2>
			<input class="hidden" name="contactId" value="<%=idString %>" />
			<section class="form-section">
				<div class="form-group">
			      	<label for="firstName">First Name: <span class="required">*</span></label>
			      	<input type="text" class="form-control" name="firstName" id="firstName" 
			      					placeholder="Enter First Name" value="<%=fnStr %>" />
			    </div>
			    <div class="form-group">
			      	<label for="lastName">Last Name: <span class="required">*</span></label>
			      	<input type="text" class="form-control" name="lastName" id="lastName" 
			      					placeholder="Enter Last Name" value="<%=lnStr %>" />
			    </div>
		    </section>
		    <section class="form-section">
		    	<h3>Address</h3>
			    <div class="form-group">
			    	<label for="street">Street: <span class="required">*</span></label>
			    	<input type="text" class="form-control" name="street" id="street" 
			    			placeholder="Enter Address (Ex: 124 First Avenue)" value="<%=strStr %>"  />
			    </div>
			     <div class="form-group">
			    	<label for="city">City: <span class="required">*</span></label>
			    	<input type="text" class="form-control" name="city" id="city" 
			    			placeholder="Enter City" value="<%=cityStr %>"  />
			    </div>
			    <div class="two-col">
			    	<div class="form-group">
				    	<label for="state">State: <span class="required">*</span></label>
				    	<t:stateSelect state="<%=stateStr %>"></t:stateSelect>
				    </div>
				    <div class="form-group">
				    	<label for="postalCode">Postal Code: <span class="required">*</span></label>
				    	<input type="text" class="form-control" name="postalCode" id="postalCode" 
				    			placeholder="Enter Postal Code (Ex: 12345)" maxLength="5" value="<%=poStr %>" />
				    </div>
			    </div>
			     <input class="hidden" name="addressId" id="addressId" value="<%=addressIdString %>" />
			</section>   
			<div class="button-container">
				<span class="btn btn-large action" id="backToMain">Back</span>
				<input type="submit" class="btn btn-large save" value="Save" />
			</div>  
			<span class="required">* denotes required field.</span>
		</div>
		<div class="right">
			<section id="emailSection" class="form-section">
				<h2>Email Addresses</h2>
				<span id="addEmailBtn" class="btn action">+ Add Email</span>
				<% 
					for (i = 0; i < numEmails;i++) { 
						Email thisEmail = contact.getEmails().get(i);
						String emailId = Integer.toString(thisEmail.getId());
				%>
				<t:emailGroup number="${Integer.toString(Integer.toString(i+1))}" emailAddress="<%=thisEmail.getAddress() %>" 
						emailType="<%=thisEmail.getType() %>" emailId="<%=emailId %>"></t:emailGroup>
				<% 
					} 
					for (i = numEmails; i < 5;i++) {  	
				%>
					<t:emailGroup number="<%=Integer.toString(i+1) %>" emailAddress="" 
							emailType="" className="hidden" emailId="0"></t:emailGroup>
				<% } %>
			</section>
			<section id="phoneSection" class="form-section">
				<h2>Phone Numbers</h2>
				<span id="addPhoneBtn" class="btn action">+ Add Phone</span>
				<% 
					for (i = 0; i < numPhones;i++) { 
						Phone thisPhone = contact.getPhoneNumbers().get(i);
						String phoneId = Integer.toString(thisPhone.getId());
				%>
	
				<t:phoneGroup number="<%=Integer.toString(i+1) %>" phoneNumber="<%=thisPhone.getNumber() %>" 
						phoneType="<%=thisPhone.getType() %>" phoneId="<%=phoneId %>"></t:phoneGroup>
				<% 
					} 
					for (i = numPhones; i < 5;i++) {  	
				%>
				<t:phoneGroup number="<%=Integer.toString(i+1) %>" phoneNumber="" 
						phoneType="" className="hidden" phoneId="0"></t:phoneGroup>
				<% } %>
			</section>
			<section id="dateSection" class="form-section">
				<h2>Important Dates</h2>
				<span id="addDateBtn" class="btn action">+ Add Date</span>
				<% 
					String dateIndex = "";
					for (i = 0; i < numDates;i++) {  
						ContactDate thisDate = contact.getContactDates().get(i);
						String dateId = Integer.toString(thisDate.getId());
						
				%>
				<t:contactDateGroup number="<%=Integer.toString(i+1) %>" contactDate="<%=thisDate.getHtmlString() %>" 
						contactDateType="<%=thisDate.getType() %>" dateId="<%=dateId %>"></t:contactDateGroup>
				<% 
					} 
					for (i = numDates; i < 5;i++) {  
						dateIndex = Integer.toString(i+1);
				%>
				<t:contactDateGroup number="<%=Integer.toString(i+1) %>" contactDate="" 
						contactDateType="" className="hidden" dateId="0"></t:contactDateGroup>
				<% } %>
			</section>
	</div>
	<div class="push"></div>
	
	</form>
</div>
<t:footer></t:footer>