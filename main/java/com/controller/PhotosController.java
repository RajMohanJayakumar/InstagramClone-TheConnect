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

import com.externalOperation.DatastoreOperation;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.model.Feed;

@WebServlet("/photos")
public class PhotosController extends HttpServlet {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	ObjectMapper mapper = new ObjectMapper();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		Query q = new Query("Feed").addFilter("userId", FilterOperator.EQUAL, session.getAttribute("userId"));
		q.addSort("timeStamp", Query.SortDirection.DESCENDING);
		List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(500));
		List<Feed> feeds =  DatastoreOperation.EntitiesListToObjectList(preparedQuery,"Feed");
		String json = mapper.writeValueAsString(feeds);
		response.setContentType("application/json");
		response.getWriter().print(json);
			
	}
}
