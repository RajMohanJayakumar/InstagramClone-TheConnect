package com.zOldfilters;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class SessionFilter
 */
@WebFilter("/SessionFilter")
public class SessionFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//ttpServletResponse res = (HttpServletResponse)response;
		
		//HttpSession session = req.getSession(false);
		
//		if(session == null) {
//			if(req.getParameter("email")==null) {
//				System.out.println("null emailNull");
////				res.sendRedirect("http://localhost:8080/login.html");
//				req.getRequestDispatcher("/login");
//			}
//			else {
//				session = req.getSession();
//				session.setAttribute("email",req.getParameter("email"));
//				System.out.println("null emailPresent");
//				sessionEmail.put(session, req.getParameter("email"));
////				res.sendRedirect("http://localhost:8080/app.html");
//				req.getRequestDispatcher("/index");
//			}
//		}
//		else {
//			if(request.getParameter("email").equals(sessionEmail.get(session))) {
//				System.out.println("present emailequal");
////				res.sendRedirect("http://localhost:8080/app.html");
//				req.getRequestDispatcher("/index");
//			}
//			else {
//				session.setAttribute("email",req.getParameter("email"));
//				System.out.println("present emailunequal");
//				sessionEmail.put(session, req.getParameter("email"));
////				res.sendRedirect("http://localhost:8080/app.html");
//				req.getRequestDispatcher("/index");
//			}
//		}
//		
//		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
