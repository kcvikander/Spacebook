package com.cvikander.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(
		dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD
		}
					, 
		urlPatterns = { 
				"/main.jsp", 
				"/edit.jsp"
		})
public class AuthenticationFilter implements Filter {
	FilterConfig fc;

    public AuthenticationFilter() { }

	public void destroy() {	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String message = "You must be logged in to access this page.";
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		String forwardPage = "/home.jsp";
		
		synchronized(session){
			String reqUri = req.getRequestURI();
			if (reqUri.contains("main.jsp") || reqUri.contains("edit.jsp")){
				if (session.getAttribute("user") == null){
					request.setAttribute("message", message);
					fc.getServletContext().getRequestDispatcher(forwardPage).
						forward(request, response);
				}
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		fc = fConfig;
	}

}
