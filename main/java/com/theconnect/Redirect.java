package com.theconnect;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Redirect
 */
//@WebServlet("/Redirect")
public class Redirect extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HelloRedirect");
//		String url = (String)request.getParameter("url");
//		String url = (String)request.getParameter("url");
//		System.out.println(url);
		System.out.println((String)request.getAttribute("redirect"));
		if(((String)request.getAttribute("redirect")).equals("1"))
		request.getRequestDispatcher("/index").include(request, response);
		else
			request.getRequestDispatcher("/login").include(request, response);
//		response.sendRedirect(location);
	}
}