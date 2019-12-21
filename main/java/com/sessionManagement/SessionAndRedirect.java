package com.sessionManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.externalOperation.DatastoreOperation;
import com.externalOperation.JsonPharsingOperation;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.model.Feed;
import com.model.UserCredential;
import com.model.UserDetail;

@WebServlet("/session")
public class SessionAndRedirect extends HttpServlet {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute("userId") != null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserDetail userDetail = (UserDetail) JsonPharsingOperation.jsonToObject(request, "UserDetail","asSingleObject");

		if(userDetail.getUserId() == null || userDetail.getEmail() == null || userDetail.getName() == null || userDetail.getProPicUrl() == null) {
			response.setStatus(400);
			return;
		}
		
		Query q = new Query("UserCredential").addFilter("userId", FilterOperator.EQUAL, userDetail.getUserId());
		List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));
		if(preparedQuery.isEmpty()) {
			String arr[] = userDetail.getEmail().split("@");
			UserCredential userCredential = new UserCredential();
			userCredential.setUserId(userDetail.getUserId());
			userCredential.setUsername(userDetail.getEmail());
			userCredential.setPassword(arr[0]);
			DatastoreOperation.ObjectToDatastore(userCredential,"UserCredential");
		}
		HttpSession session = request.getSession();
		DatastoreOperation.ObjectToDatastore(userDetail,"UserDetail");
		session.setAttribute("userId", userDetail.getUserId());
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session != null)
		session.invalidate();
	}

}