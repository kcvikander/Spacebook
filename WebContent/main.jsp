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
    			java.util.List,
    			java.text.SimpleDateFormat,
    			java.util.Date" 
    %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
	User user = (User)session.getAttribute("user");
	ContactList contactList = (ContactList)request.getAttribute("contactList");
	if (contactList == null){
		contactList = ContactService.getContactsForUser(user.getUserId());
	}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String message = "";
	String messageClass = ""; //1 = success, 2=error
	if ((String)request.getAttribute("message") != null){
		message = (String)request.getAttribute("message");
		if (message.equals("You must be logged in to access this page.")){
			messageClass = "error";
		} else {
			messageClass = "success";
		}
	}
%>
<t:header title="Spacebook"></t:header>
<div class="main">
	<% if (message.length() > 0) { %>
	<div class="message ${messageClass}"><%=message %></div>
	<% } %>
	<div class="button-container">
		<form action="AddContact" method="get">
			<input class="btn btn-large action" id="add" type="submit" value="Add Contact" />
		</form> 
	</div>
	<div class="contact-container">
	
	<% 
		if (contactList == null){
	%>
	<div class="no-contacts">
		There are no contacts for this user.  Add one by clicking the button above.
	</div>
	<%
		} else if (contactList != null) {
			for (Contact contact : contactList.getContacts()) {
				String idStr = Integer.toString(contact.getContactId());
 	%> 
		 	<div class="contact-tile">
		 		<div class="contact-actions">
		 			<form action="EditContact" method="post">
			 			<input class="btn btn-small action" id="<%=idStr %>" type="submit" value="Edit" />
			 			<input type="hidden" name="editId" value="<%=idStr %>" />
		 			</form>
		 			<form action="DeleteContact" method="post">
			 			<input class="btn btn-small delete" id="<%=idStr %>" type="submit" value="Delete" />
			 			<input type="hidden" name="deleteId" value="<%=idStr %>" />
			 		</form>
		 		</div>
		 		<div class="contact-info">
		 			<h3><%= contact.getFirstName() %> <%= contact.getLastName() %></h3>
		 			<div class="address">
		 				<span>  <%= contact.getAddress().getStreet() %><br /> </span>
					   	<span>  <%= contact.getAddress().getCity() %>,  <%= contact.getAddress().getState() %>  <%= contact.getAddress().getPostalCode() %></span>
		 			</div>
					<section class="contact-section">
						<h4>Phone Numbers</h4>
						<% 	if (contact.getPhoneNumbers().size() > 0) {
					 			for (Phone phone : contact.getPhoneNumbers() ) {
					 	%> 
					 		<div class="contact-data">[<%= phone.getType() %>] <%= phone.getNumber() %></div>
					 	<% 
					 			}
							} else {
					 	%> 
					 	<span class="none-msg">There are no phone numbers for this contact.</span>
					 	<% 	} %>
					</section>
					<section class="contact-section">
						<h4>Email Addresses</h4>
						<% 	if (contact.getEmails().size() > 0) {
					 			for (Email email : contact.getEmails() ) {
					 	%> 
					 		<div class="contact-data">[<%= email.getType() %>] <%= email.getAddress() %></div>
					 	<% 
					 			}
							} else {
					 	%> 
					 	<span class="none-msg">There are no email addresses for this contact.</span>
					 	<% 	} %>
					</section>
					<section class="contact-section">
						<h4>Important Dates</h4>
						<% 	if (contact.getContactDates().size() > 0) {
					 			for (ContactDate dates : contact.getContactDates() ) {
					 	%> 
					 		<div class="contact-data">[<%= dates.getType() %>] <%= dates.getDateString() %></div>
					 	<% 
					 			}
							} else {
					 	%> 
					 	<span class="none-msg">There are no important dates for this contact.</span>
					 	<% 	} %>
					</section>
		 		</div>
		 		
		 	</div>
 	<% 
			}
 		}
 	%> 
	
	</div>
</div>
<t:footer></t:footer>