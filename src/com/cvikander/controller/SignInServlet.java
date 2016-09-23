package com.cvikander.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cvikander.service.UserService;
import com.cvikander.model.User;

/**
 * Servlet implementation class SignInServlet
 */
@WebServlet("/SignIn")
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public HttpSession session;
	public UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignInServlet() {
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
		String userName, password;
		userName = request.getParameter("loginUser").toString();
		password = request.getParameter("loginPassword").toString();
		
		User user = UserService.tryLogin(userName, password);
		
		if (user != null){
			//Logged in
			session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect("main.jsp");
		} else {
			// invalid
			request.setAttribute("error", "Username and Password combination was not found");
			RequestDispatcher errorDispatcher = request.getRequestDispatcher("/home.jsp");
			errorDispatcher.forward(request, response);
		}
	}

}
