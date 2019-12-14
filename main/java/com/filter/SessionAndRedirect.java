package com.filter;
//package com.sessionsAndRedirections;
//
//import com.models.UserDetails;
//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.google.appengine.api.datastore.DatastoreService;
//import com.google.appengine.api.datastore.DatastoreServiceFactory;
//
///**
// * Servlet implementation class SessionAndRedirect
// */
//@WebServlet("/SessionAndRedirect")
//public class SessionAndRedirect extends HttpServlet {
//	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//	UserDetails userDetails = new UserDetails();
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession(false);
//		
//		if(session == null) {
//			
//			if(userDetails.getEmail()==null) {
//				System.out.println("null emailNull");
//				writer.append("null");
//			}
//			else {
//				session = request.getSession();
////				session.setAttribute("email", new HashMap());
//				System.out.println("null emailPresent");
//				
//				sessionEmail.put(session.getId(), userDetails.getEmail());
//				sessionDetails.put(userDetails.getEmail(),userDetails);
//				
////				sessionEmail = (HashMap)session.getAttribute("email");
////				sessionEmail.put(session.getId(), userDetails.getEmail());
//				
//				
//				System.out.println(sessionEmail);
//					System.out.println(userDetails.getEmail());
//				writer.append(createKey(userDetails.getEmail()));
//			}
//		}
//		else {
//			
//			if(userDetails.getEmail()==null) {
//				
//				writer.append("null");
//				
//			}else {
//			
////			sessionEmail = (HashMap) session.getAttribute("email");
//			
//			
////			if(sessionEmail.get(session.getId())==userDetails.getEmail()) {
//////				session.setAttribute("email", new HashMap());
//////				sessionEmail = (HashMap)session.getAttribute("email");
//////				sessionEmail.put(session.getId(), userDetails.getEmail());
////				
//////				session = request.getSession();
//////				sessionEmail.put(session.getId(), userDetails.getEmail());
//////				sessionDetails.put(userDetails.getEmail(),userDetails);
//////				
//////				System.out.println(sessionEmail);
//////				System.out.println(sessionDetails);
////				
////				System.out.println("sessionNull emailPrrest");
////				
////				writer.append(createKey(userDetails.getEmail()));
////
////			}
////			else {
//			//write for if jessionid is present and email is null
//			if(userDetails.getEmail()==sessionEmail.get(session.getId())) {
//				System.out.println("present emailequal");
//				System.out.println(userDetails.getEmail());
//				writer.append(createKey(userDetails.getEmail()));
//				
//			}
//			else {
//				System.out.println("present emailunequal");
////				sessionEmail.put(session.getId(), userDetails.getEmail());
//				
//				sessionEmail.put(session.getId(), userDetails.getEmail());
//				sessionDetails.put(userDetails.getEmail(),userDetails);
//				
////				sessionDetails.put(userDetails.getEmail(),userDetails);
//				
//				System.out.println(sessionEmail);
//				
//				writer.append(createKey(userDetails.getEmail()));
//				
//			}
//			}
//
//		
//	}
//	
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//	}
//
//}
