package com.cvikander.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cvikander.model.Address;
import com.cvikander.model.Contact;
import com.cvikander.model.ContactDate;
import com.cvikander.model.Email;
import com.cvikander.model.Phone;
import com.cvikander.model.User;
import com.cvikander.service.ContactService;

/**
 * Servlet implementation class SaveContactServlet
 */
@WebServlet("/SaveContact")
public class SaveContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public HttpSession session;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveContactServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean existingContact = false;
		String message = "";
		session = request.getSession();
		User user = (User) session.getAttribute("user");
		Contact contact = new Contact(user.getUserId());
		int contactId;
		if (request.getParameter("contactId").toString().length() > 0){
			contactId = Integer.parseInt(request.getParameter("contactId").toString());
		} else {
			contactId = 0;
		}
		if (contactId > 0){
			contact.setContactId(contactId);
			existingContact = true;
		}
		
		contact.setFirstName(request.getParameter("firstName").toString());
		contact.setLastName(request.getParameter("lastName").toString());
		Address address = new Address();
		address.setStreet(request.getParameter("street").toString());
		address.setCity(request.getParameter("city").toString());
		address.setState(request.getParameter("state").toString());
		address.setPostalCode(request.getParameter("postalCode").toString());
		address.setId(Integer.parseInt(request.getParameter("addressId")));
		contact.setAddress(address);

		for (int i=1;i<=5;i++){
			if (request.getParameter("emailAddress_"+Integer.toString(i)).length() > 0){
				Email thisEmail = new Email();
				thisEmail.setAddress(request.getParameter("emailAddress_"+Integer.toString(i)));
				thisEmail.setType(request.getParameter("emailAddressType_"+Integer.toString(i)));
				thisEmail.setId(Integer.parseInt(request.getParameter("emailAddressId_"+Integer.toString(i))));
				contact.getEmails().add(thisEmail);
			}
		}
		for (int i=1;i<=5;i++){
			if (request.getParameter("phoneNumber_"+Integer.toString(i)).length() > 0){
				Phone thisPhone = new Phone();
				thisPhone.setNumber(request.getParameter("phoneNumber_"+Integer.toString(i)));
				thisPhone.setType(request.getParameter("phoneNumberType_"+Integer.toString(i)));
				thisPhone.setId(Integer.parseInt(request.getParameter("phoneNumberId_"+Integer.toString(i))));
				contact.getPhoneNumbers().add(thisPhone);
			}
		}

	    DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
		for (int i=1;i<=5;i++){
			if (request.getParameter("contactDate_"+Integer.toString(i)).length() > 0){
				ContactDate thisDate = new ContactDate();
				Date tempDateObj = null;
				try {
					tempDateObj = df.parse(request.getParameter("contactDate_"+Integer.toString(i)));
				} catch (ParseException pe){
					tempDateObj = new Date();
				}
				thisDate.setDate(tempDateObj);
				thisDate.setType(request.getParameter("contactDateType_"+Integer.toString(i)));
				thisDate.setId(Integer.parseInt(request.getParameter("contactDateId_"+Integer.toString(i))));
				contact.getContactDates().add(thisDate);
			}
		}
		try {
			if (existingContact){
				ContactService.updateContact(contact);
				message = "Contact updated successfully.";
			} else {
				ContactService.saveContact(contact);
				message = "Contact saved successfully.";
			}
		} catch (Exception e){
			if (existingContact){
				message = "There was a problem updating the contact, please try again.";
			} else {
				message = "There was a problem saving the contact, please try again.";
			}
		}
		
		request.setAttribute("message", message);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
		dispatcher.forward(request, response);		
	}
}
