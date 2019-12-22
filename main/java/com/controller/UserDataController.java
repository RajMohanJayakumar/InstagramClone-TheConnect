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
public class UserDataController extends HttpServlet {
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserDetail userDetail = null;
		List<UserDetail> userDetails = null;
		String json = null;
		
		System.out.println("session "+session);
		System.out.println("userDetail "+userDetail);
		System.out.println("userDetails "+userDetails);
		String user = request.getParameter("user");
		if(user.equals("null")) {
			System.out.println("user is null "+user);
			response.setStatus(400);
			return;
		}
		
		switch(user) {
		case "currentuser" : {
			System.out.println("currentuser ");
			System.out.println("session Attribute "+session.getAttribute("userId"));
			Query q = new Query("UserDetail").addFilter("userId", FilterOperator.EQUAL, session.getAttribute("userId"));
			System.out.println("Query "+q);
			List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(4));
			System.out.println("preparedQuery "+preparedQuery);
			if(!preparedQuery.isEmpty()) {
			userDetail = (UserDetail) DatastoreOperation.EntitiesListToObjectList(preparedQuery,"UserDetail","asSingleObject");
			}
			else {
				response.setStatus(202);
				return;
			}
			System.out.println("currentuser "+userDetail.getName());
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
		
		if(userDetail != null) {
			System.out.println("userDetail not null "+userDetail);
			json = mapper.writeValueAsString(userDetail);
		}
		else {
			json = mapper.writeValueAsString(userDetails);
		}
		
		System.out.println("json"+json);
		response.setContentType("application/json");
		response.getWriter().print(json);
}
}
