package com.cvikander.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cvikander.model.User;
import com.cvikander.model.Email;
import com.cvikander.model.Phone;
import com.cvikander.model.Address;
import com.cvikander.service.UserService;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet(description = "Servlet used for signing up new users", urlPatterns = { "/SignUp" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public HttpSession session;
	public UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		session = request.getSession();
		User user = new User();
		Email emailAddress = new Email();
		Phone phoneNumber = new Phone();
		Address address = new Address();
		session.setAttribute("user", user);

		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		address.setStreet(request.getParameter("street"));
		address.setCity(request.getParameter("city"));
		address.setState(request.getParameter("state"));
		address.setPostalCode(request.getParameter("postalCode"));
		phoneNumber.setNumber(request.getParameter("phoneNumber").toString());
		phoneNumber.setType("Other");	
		emailAddress.setAddress(request.getParameter("emailAddress").toString());
		emailAddress.setType("Other");
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
	
		user.setEmail(emailAddress);
		user.setAddress(address);
		user.setPhone(phoneNumber);
		try {
			UserService.saveUser(user);
		} catch (Exception e){
			request.setAttribute("error", e.toString());
			RequestDispatcher errorDispatcher = request.getRequestDispatcher("/home.jsp");
			errorDispatcher.forward(request, response);
		}
		session.setAttribute("user", user);
		response.sendRedirect("main.jsp");
	}
}
