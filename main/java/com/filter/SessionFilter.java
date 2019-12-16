package com.filter;

import java.io.IOException;
import java.util.List;

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

import com.externalOperations.DatastoreOperations;
import com.externalOperations.JsonOperations;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.model.*;

/**
 * Servlet Filter implementation class sessionAndKeyFilter
 */
@WebFilter("/sessionAndKeyFilter")
public class SessionFilter implements Filter {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletResponse res = (HttpServletResponse)response;
		HttpServletRequest req = (HttpServletRequest)request;
		
		HttpSession httpSession = req.getSession(false);
		String session = String.valueOf(httpSession);
		
		System.out.println("Session"+req.getMethod());
		
//		SessionLog sessionLog = new SessionLog();
//		sessionLog.setSessionId(session);
//		sessionLog.setKey("1234");
//		sessionLog.setUserId("1234");

		if(req.getMethod().equals("GET")) {
			if(session != null && httpSession.getAttribute("userId") != null) {
				
			}
			else {
				System.out.println("Session = Null");
				req.getRequestDispatcher("/login").forward(req, res);
			}
		}
		
		if(req.getMethod().equals("POST")){
			System.out.println("Inside Post Filter");
//			UserDetail userDetail = (UserDetail) JsonOperations.jsonToObject(req, "UserDetail", "asSingleObject");
//			System.out.println(userDetail);
//			if(userDetail.getUserId() == null || userDetail.getEmail() == null || userDetail.getName() == null || userDetail.getProPicUrl() == null) {
//				req.getRequestDispatcher("/login").forward(req, res);
//			}
			
			httpSession = req.getSession();
			UserDetail userDetail = (UserDetail) JsonOperations.jsonToObject(req, "UserDetail","asSingleObject");
			System.out.println(userDetail.getEmail()); 
			DatastoreOperations.ObjectToDatastore(userDetail,"UserDetail");
			httpSession.setAttribute("userId", userDetail.getUserId());
		}
		
		System.out.println(req.getMethod());
		
		
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}