package com.zOldtheconnect;

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

import com.googlecode.objectify.ObjectifyService;
import com.model.UserDetail;
import com.zOldaddpost.AddFeed;
import com.zOlduserdetails.SendUserDetailsPojo;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
//import com.google.appengine.api.datastore.TransactionOptions;

public class App extends HttpServlet {

	HashMap<String,String> sessionEmail = new HashMap<String,String>();
	HashMap<String,String> sessionKeys = new HashMap<String,String>();
	HashMap<String,UserDetail> sessionDetails = new HashMap<String,UserDetail>();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("App Servlet");

		String str = jsonToString(request);
		UserDetail userDetails = jsonPharse(str);
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
					System.out.println(userDetails.getEmail());
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
		
		HttpSession session = request.getSession(false);
		System.out.println(request.getParameter("key"));
		
		if(sessionKeys.containsKey(request.getParameter("key")) && session != null) {
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
		
		UserDetail userDetails = sessionDetails.get(sessionEmail.get(session.getId()));
		
		System.out.println(userDetails);
		
		String json = ow.writeValueAsString(userDetails);
		response.setContentType("application/json");
		response.getWriter().print(json);
		System.out.println("getOne Successful");
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		System.out.println(request.getParameter("key"));
		String key[] = request.getParameter("key").split("=");
		System.out.println(key[1]);
		if(sessionKeys.containsKey(key[1])) {
			sessionKeys.remove(key[1]);
			System.out.println(session);
			session.invalidate();
			System.out.println(session);
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
	public UserDetail jsonPharse(String str) throws IOException {
		
		//Creating reference to store the contact variable
		UserDetail userDetails = null;
		
	      ObjectMapper mapper = new ObjectMapper();
	      
	      try
	      {
	         userDetails =  mapper.readValue(str.getBytes(),UserDetail.class);
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
	
	protected void doPatch(HttpServletRequest request, HttpServletResponse response) {
		DatastoreService datastore= DatastoreServiceFactory.getDatastoreService();
	    
	    //creating Entity with out Id (Numeric id will be generated by datastore)
	    // allow duplication in datastore
	    		
	    		System.out.println("Pushing entity without custom Id\n");
			    Entity studentEntity1 = new Entity("Student","fdad");
			    studentEntity1.setProperty("name", "Ajay");
			    studentEntity1.setProperty("age", 18);
			    studentEntity1.setProperty("Rat", 18);
			    studentEntity1.setProperty("class", 8);
			    studentEntity1.setProperty("place", "Chennai");
			    System.out.println(studentEntity1);
			    
		//storing entity to the datastore
	    
	    		datastore.put(studentEntity1);
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