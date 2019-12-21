package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.externalOperation.DatastoreOperation;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;
import com.model.Feed;

/**
 * Servlet implementation class FriendsActivity
 */
@WebServlet("/friends")
public class FriendsController extends HttpServlet {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	ObjectMapper mapper = new ObjectMapper();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Query q = new Query("UserDetail");
		List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(500));
		List<Feed> feeds =  DatastoreOperation.EntitiesListToObjectList(preparedQuery,"UserDetail");
		String json = mapper.writeValueAsString(feeds);
		response.setContentType("application/json");
		response.getWriter().print(json);
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(2);
		String startCursor = request.getParameter("cursor");
	    if (startCursor != null) {
	      fetchOptions.cursor(Cursor.fromWebSafeString(startCursor));
	    }

	    Query q = new Query("SELECT userId FROM Feed");
	    PreparedQuery pq = datastore.prepare(q);

	    QueryResultList<Entity> results;
	      results = (QueryResultList<Entity>)pq.asQueryResultList(fetchOptions);
	      System.out.println(results);
	      System.out.println(pq);
	}
}