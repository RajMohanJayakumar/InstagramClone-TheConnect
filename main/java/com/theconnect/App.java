package com.theconnect;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.googlecode.objectify.ObjectifyService;

public class App extends HttpServlet {

	
	HashMap sessionEmail;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
		
//		HttpServletRequest req = (HttpServletRequest)request;
//		HttpServletResponse res = (HttpServletResponse)response;
		
		System.out.println("App Servlet");
		
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("email"));
		System.out.println(request.getParameter("proPic"));
		
		HttpSession session = request.getSession(false);
		System.out.println(session);
		
		if(session == null) {
			
			if(request.getParameter("email")==null) {
				System.out.println("null emailNull");
				request.getRequestDispatcher("/login");
			}
			else {
				session = request.getSession();
				session.setAttribute("email", new HashMap());
				System.out.println("null emailPresent");
				sessionEmail = (HashMap)session.getAttribute("email");
				sessionEmail.put(session.getId(), request.getParameter("email"));
				System.out.println(sessionEmail);
				
//				System.out.println(session.getAttribute("id"));
				
				request.getRequestDispatcher("/index");
			}
		}
		else {
			
			sessionEmail = (HashMap)session.getAttribute("email");
			
			if(request.getParameter("email").equals(sessionEmail.get(session.getId()))) {
				System.out.println("present emailequal");
				request.getRequestDispatcher("/index");
			}
			else {
//				session.setAttribute("email",request.getParameter("email"));
				System.out.println("present emailunequal");
				sessionEmail.put(session.getId(), request.getParameter("email"));
				request.getRequestDispatcher("/index");
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
//		ObjectifyService.begin().
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