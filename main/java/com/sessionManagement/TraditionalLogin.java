package com.sessionManagement;

import java.io.IOException;
import java.util.List;

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
import com.model.UserCredential;
import com.model.UserDetail;

/**
 * Servlet implementation class TraditionalLogin
 */
@WebServlet("/traditionalLogin")
public class TraditionalLogin extends HttpServlet {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserCredential appliedUserCredential = (UserCredential) JsonPharsingOperation.jsonToObject(request, "UserCredential","asSingleObject");
		
		Query q = new Query("UserCredential").addFilter("username", FilterOperator.EQUAL, appliedUserCredential.getUsername());
		List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));
		if(preparedQuery.isEmpty()) {
			response.setStatus(403);
			return;
		}
		else {
			UserCredential storedUserCredential = (UserCredential) DatastoreOperation.EntitiesListToObjectList(preparedQuery,"UserCredential","asSingleObject");
			if(appliedUserCredential.getUsername().equals(storedUserCredential.getUsername()) && appliedUserCredential.getPassword().equals(storedUserCredential.getPassword())){
				HttpSession session = request.getSession();
				session.setAttribute("userId", storedUserCredential.getUserId());
			}
		}
	}

}
