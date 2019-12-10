package com.theconnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.addpost.AddFeed;
import com.googlecode.objectify.ObjectifyService;

public class App extends HttpServlet {

	HashMap sessionEmail;
	HashMap sessionKeys = new HashMap();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
		
//		HttpServletRequest req = (HttpServletRequest)request;
//		HttpServletResponse res = (HttpServletResponse)response;
		
//		request.getRequestDispatcher("index.jsp").forward(request, response);
		
		System.out.println("App Servlet");
//		RequestDispatcher dispatch = request.getRequestDispatcher("/index.jsp");
//		dispatch.forward(request, response);
		
		String str = jsonToString(request);
		UserDetails userDetails = jsonPharse(str);
		System.out.println(userDetails.getId());
		System.out.println(userDetails.getName());
		System.out.println(userDetails.getEmail());
		System.out.println(userDetails.getImgUrl());
		response.setContentType("text/plain");
		PrintWriter writer=response.getWriter();
		
		HttpSession session = request.getSession(false);
		System.out.println(session);
		
		if(session == null) {
			
			if(request.getParameter("email")==null) {
				System.out.println("null emailNull");
				writer.append("null");
			}
			else {
				session = request.getSession();
				session.setAttribute("email", new HashMap());
				System.out.println("null emailPresent");
				sessionEmail = (HashMap)session.getAttribute("email");
				sessionEmail.put(session.getId(), request.getParameter("email"));
				System.out.println(sessionEmail);
					
//				System.out.println(session.getAttribute("id"));
				writer.append(createKey(request.getParameter("email")));
//				request.setAttribute("redirect", "1");
				request.getRequestDispatcher("/index").include(request, response);
			}
		}
		else {
			
			if(userDetails.getEmail()==null) {

//				request.setAttribute("redirect", "2");
				
				writer.append("null");
				
//				request.getRequestDispatcher("/login").include(request, response);
//				request.getRequestDispatcher("/login").forward(request, response);
			}else {
			
			sessionEmail = (HashMap) session.getAttribute("email");
			
			
			if(sessionEmail==null) {
				session.setAttribute("email", new HashMap());
				sessionEmail = (HashMap)session.getAttribute("email");
				sessionEmail.put(session.getId(), request.getParameter("email"));
				System.out.println("sessionNull emailPrrest");
				
				writer.append(createKey(request.getParameter("email")));
				//RequestDispatcher dispatch = request.getRequestDispatcher("/webapp/index.jsp");
//				dispatch.forward(request, response);
			}
			else {
			//write for if jessionid is present and email is null
			System.out.println("11"+request.getParameter("email"));
			System.out.println("22"+sessionEmail.get(session.getId()));
			if(userDetails.getEmail()==sessionEmail.get(session.getId())) {
				System.out.println("present emailequal");
				
//				response.sendRedirect("/index");
//				request.getRequestDispatcher("/index").include(request,response);
				
				writer.append(createKey(request.getParameter("email")));
				
				//RequestDispatcher dispatch = request.getRequestDispatcher("/webapp/index.jsp");
//				dispatch.forward(request, response);
			}
			else {
//				session.setAttribute("email",request.getParameter("email"));
				System.out.println("present emailunequal");
				sessionEmail.put(session.getId(), request.getParameter("email"));
//				request.setAttribute("redirect", "1");
				System.out.println(sessionEmail);
//				request.getRequestDispatcher("/index").include(request, response);
				
				writer.append(createKey(request.getParameter("email")));
				
				System.out.println("pU");
			}
			}
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		System.out.println(request.getParameter("key"));
		
		if(sessionKeys.containsKey(request.getParameter("key"))) {
			String email = (String) sessionKeys.get(request.getParameter("key"));
			System.out.println("Enter from Key "+email);
			sessionKeys.remove(request.getParameter("key"));
			request.getRequestDispatcher("/index").include(request, response);
		}
		else {
			request.getRequestDispatcher("/login").include(request, response);
		}
	}
	
		
	public String createKey(String email) {
		
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<20;i++) {
			int index = (int)(alphaNumeric.length()*Math.random());
			sb.append(alphaNumeric.charAt(index));
		}
		
		String str = sb.toString();
		
		sessionKeys.put(str,email);
		
		System.out.println(str);
		
		return str;
	}
		
	
	public String jsonToString(HttpServletRequest request) throws IOException {
		String line = "";
		StringBuffer str = new StringBuffer();
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null)
			{
			str.append(line);
			}
		String jsonString = str.toString();
		return jsonString;
	}
	
	//Pharsing a json standard string format to an Object(Contact)
	public UserDetails jsonPharse(String str) throws IOException {
		
		//Creating reference to store the contact variable
		UserDetails userDetails = null;
		
	      ObjectMapper mapper = new ObjectMapper();
	      
	      try
	      {
	         userDetails =  mapper.readValue(str.getBytes(),UserDetails.class);
	      } catch (JsonGenerationException e)
	      {
	         e.printStackTrace();
	      } catch (JsonMappingException e)
	      {
	         e.printStackTrace(); 
	      } catch (IOException e)
	      {
	         e.printStackTrace();
	      }
	      return userDetails;
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