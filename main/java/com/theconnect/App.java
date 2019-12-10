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
import org.codehaus.jackson.map.ObjectWriter;

import com.addpost.AddFeed;
import com.googlecode.objectify.ObjectifyService;
import com.userdetails.SendUserDetailsPojo;

public class App extends HttpServlet {

	HashMap<String,String> sessionEmail = new HashMap<String,String>();
	HashMap<String,String> sessionKeys = new HashMap<String,String>();
	HashMap<String,UserDetails> sessionDetails = new HashMap<String,UserDetails>();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("App Servlet");

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
			
			if(userDetails.getEmail()==null) {
				System.out.println("null emailNull");
				writer.append("null");
			}
			else {
				session = request.getSession();
//				session.setAttribute("email", new HashMap());
				System.out.println("null emailPresent");
				
				sessionEmail.put(session.getId(), userDetails.getEmail());
				sessionDetails.put(userDetails.getEmail(),userDetails);
				
//				sessionEmail = (HashMap)session.getAttribute("email");
//				sessionEmail.put(session.getId(), userDetails.getEmail());
				
				
				System.out.println(sessionEmail);
					System.out .println(userDetails.getEmail());
				writer.append(createKey(userDetails.getEmail()));
			}
		}
		else {
			
			if(userDetails.getEmail()==null) {
				
				writer.append("null");
				
			}else {
			
//			sessionEmail = (HashMap) session.getAttribute("email");
			
			
//			if(sessionEmail.get(session.getId())==userDetails.getEmail()) {
////				session.setAttribute("email", new HashMap());
////				sessionEmail = (HashMap)session.getAttribute("email");
////				sessionEmail.put(session.getId(), userDetails.getEmail());
//				
////				session = request.getSession();
////				sessionEmail.put(session.getId(), userDetails.getEmail());
////				sessionDetails.put(userDetails.getEmail(),userDetails);
////				
////				System.out.println(sessionEmail);
////				System.out.println(sessionDetails);
//				
//				System.out.println("sessionNull emailPrrest");
//				
//				writer.append(createKey(userDetails.getEmail()));
//
//			}
//			else {
			//write for if jessionid is present and email is null
			if(userDetails.getEmail()==sessionEmail.get(session.getId())) {
				System.out.println("present emailequal");
				System.out.println(userDetails.getEmail());
				writer.append(createKey(userDetails.getEmail()));
				
			}
			else {
				System.out.println("present emailunequal");
//				sessionEmail.put(session.getId(), userDetails.getEmail());
				
				sessionEmail.put(session.getId(), userDetails.getEmail());
				sessionDetails.put(userDetails.getEmail(),userDetails);
				
//				sessionDetails.put(userDetails.getEmail(),userDetails);
				
				System.out.println(sessionEmail);
				
				writer.append(createKey(userDetails.getEmail()));
				
			}
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		System.out.println(request.getParameter("key"));
		
		if(sessionKeys.containsKey(request.getParameter("key"))) {
			String email = sessionKeys.get(request.getParameter("key"));
			request.getRequestDispatcher("/index").include(request, response);
		}
		else {
			request.getRequestDispatcher("/login").include(request, response);
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		
		HttpSession session = request.getSession(false);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		SendUserDetailsPojo sendUserDetails = new SendUserDetailsPojo("Yogeshwar","yogeswar1234@gmail.com","https://lh3.googleusercontent.com/a-/AAuE7mDZAGHznLCeuzmH9qFQeYhzXCPTfUMjL1dit8wmOs0=s96-c");
		
		System.out.println(sessionEmail.get(session.getId()));
		
		UserDetails userDetails = sessionDetails.get(sessionEmail.get(session.getId()));
		
		System.out.println(userDetails);
		
		String json = ow.writeValueAsString(userDetails);
		response.setContentType("application/json");
		response.getWriter().print(json);
		System.out.println("getOne Successful");
	}
	
		
	public String createKey(String email) {
		
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<20;i++) {
			int index = (int)(alphaNumeric.length()*Math.random());
			sb.append(alphaNumeric.charAt(index));
		}
		
		String str = sb.toString();
		System.out.println(email);
		
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