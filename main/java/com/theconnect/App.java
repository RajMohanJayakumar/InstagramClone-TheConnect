package com.theconnect;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.googlecode.objectify.ObjectifyService;

public class App extends HttpServlet {

	
	HashMap sessionEmail;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
		
//		HttpServletRequest req = (HttpServletRequest)request;
//		HttpServletResponse res = (HttpServletResponse)response;
		
//		request.getRequestDispatcher("index.jsp").forward(request, response);
		
		System.out.println("App Servlet");
//		RequestDispatcher dispatch = request.getRequestDispatcher("/index.jsp");
//		dispatch.forward(request, response);
		System.out.println(request.getParameter("id"));
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("email"));
		System.out.println(request.getParameter("proPic"));
		
		HttpSession session = request.getSession(false);
		System.out.println(session);
		
		if(session == null) {
			
			if(request.getParameter("email")==null) {
				System.out.println("null emailNull");
				request.setAttribute("redirect", "1");
				request.getRequestDispatcher("/redirect").forward(request, response);
			}
			else {
				session = request.getSession();
				session.setAttribute("email", new HashMap());
				System.out.println("null emailPresent");
				sessionEmail = (HashMap)session.getAttribute("email");
				sessionEmail.put(session.getId(), request.getParameter("email"));
				System.out.println(sessionEmail);
					
//				System.out.println(session.getAttribute("id"));

				request.setAttribute("redirect", "1");
				request.getRequestDispatcher("/redirect").forward(request, response);
			}
		}
		else {
			
			if(request.getParameter("email")==null) {

				request.setAttribute("redirect", "2");
				request.getRequestDispatcher("/redirect").forward(request, response);
//				request.getRequestDispatcher("/login").forward(request, response);
			}else {
			
			sessionEmail = (HashMap) session.getAttribute("email");
			
			
			if(sessionEmail==null) {
				session.setAttribute("email", new HashMap());
				sessionEmail = (HashMap)session.getAttribute("email");
				sessionEmail.put(session.getId(), request.getParameter("email"));
				System.out.println("sessionNull emailPrrest");
				request.setAttribute("redirect", "1");
				request.getRequestDispatcher("/redirect").forward(request, response);
				//RequestDispatcher dispatch = request.getRequestDispatcher("/webapp/index.jsp");
//				dispatch.forward(request, response);
			}
			else {
			//write for if jessionid is present and email is null
			System.out.println("11"+request.getParameter("email"));
			System.out.println("22"+sessionEmail.get(session.getId()));
			if(request.getParameter("email").equals(sessionEmail.get(session.getId()))) {
				System.out.println("present emailequal");	
				request.setAttribute("redirect", "1");
				request.getRequestDispatcher("/index").include(request,response);
				//RequestDispatcher dispatch = request.getRequestDispatcher("/webapp/index.jsp");
//				dispatch.forward(request, response);
			}
			else {
//				session.setAttribute("email",request.getParameter("email"));
				System.out.println("present emailunequal");
				sessionEmail.put(session.getId(), request.getParameter("email"));
				request.setAttribute("redirect", "1");
				request.getRequestDispatcher("/redirect").forward(request, response);
			}
			}
			}
		}
		
//		System.out.println(session.getId());
		


//		ObjectifyService.begin();
		
//		ObjectifyService.register(Person.class);
//		ObjectifyService.begin();
//		Person person = new Person("Raj Mohan","rajmohan2695@gmail.com");
//		ObjectifyService.ofy().save().entity(person);
		
		
		//Objectify ofy = (Objectify) ObjectifyService.push(p);;
		
		
//		ofy.save().en
//		ofy.save().entity(p).now();
//		ObjectifyService.begin();
//		ofy.put(p);
		
		
		
		
		
//		ObjectifyService.register(Person.class);
//		
//		ObjectifyService.register(Person.class);
//		
//		Objectify ofy = ObjectifyService.begin();
//		
//		Person p = new Person("Raj Mohan","rajmohan2695@gmail.com");
//		
//		ofy.put(p);
	}
}