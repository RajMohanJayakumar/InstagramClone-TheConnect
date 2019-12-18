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

import com.externalOperation.Datastore;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.model.UserDetail;

@WebServlet("/userinfo")
public class UserInfo extends HttpServlet {
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = request.getParameter("userId");
		if(userId == null) {
			response.setStatus(400);
			return;
		}
		
		switch(userId) {
		case "getAll":{
			Query q = new Query("UserDetail").addSort("name");
			List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(4));
			List<UserDetail> userDetails = (List<UserDetail>) Datastore.EntitiesListToObjectList(preparedQuery,"UserDetail");
			
			ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = mapper.writeValueAsString(userDetails);
			
			response.setContentType("application/json");
			response.getWriter().print(json);
		}break;
		
		default : {
		Query q = new Query("UserDetail").addFilter("userId", FilterOperator.EQUAL, userId);
		List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(4));
		UserDetail userDetail = (UserDetail) Datastore.EntitiesListToObjectList(preparedQuery,"UserDetail","asSingleObject");
		
		ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = mapper.writeValueAsString(userDetail);
		
		response.setContentType("application/json");
		response.getWriter().print(json);
	}
	}

}
}
