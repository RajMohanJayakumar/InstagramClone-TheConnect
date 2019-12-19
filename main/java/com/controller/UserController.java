package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.externalOperation.DatastoreOperation;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.model.UserDetail;

@WebServlet("/user")
public class UserController extends HttpServlet {
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserDetail userDetail = null;
		List<UserDetail> userDetails = null;
		String json = null;
		
		String user = request.getParameter("user");
		if(user == null) {
			response.setStatus(400);
			return;
		}
		
		switch(user) {
		case "currentuser" : {
			Query q = new Query("UserDetail").addFilter("userId", FilterOperator.EQUAL, session.getAttribute("userId"));
			List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(4));
			userDetail = (UserDetail) DatastoreOperation.EntitiesListToObjectList(preparedQuery,"UserDetail","asSingleObject");
		}break;
		
		case "getAll":{
			Query q = new Query("UserDetail").addSort("name");
			List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(100));
			userDetails = (List<UserDetail>) DatastoreOperation.EntitiesListToObjectList(preparedQuery,"UserDetail");
		}break;
		
		default : {
		Query q = new Query("UserDetail").addFilter("userId", FilterOperator.EQUAL, user);
		List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(4));
		userDetail = (UserDetail) DatastoreOperation.EntitiesListToObjectList(preparedQuery,"UserDetail","asSingleObject");
		
		
	}
	}
		
		if(userDetail != null)
			json = mapper.writeValueAsString(userDetail);
		else
			json = mapper.writeValueAsString(userDetails);
		
		response.setContentType("application/json");
		response.getWriter().print(json);
}
}
