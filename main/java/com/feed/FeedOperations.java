package com.feed;

import com.externalOperations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.model.Feed;
import com.zOldDatastore.Contact;

@WebServlet("/feed")
public class FeedOperations extends HttpServlet {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		
		if(userId == null) {
			Query q = new Query("Feed");
			List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(500));
			JsonOperations.EntitiesListToJsonResponse(response, preparedQuery,"Feed");
		}
		else {
			Query q = new Query("Feed").addFilter("userId", FilterOperator.EQUAL, userId);
			List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(500));
			JsonOperations.EntitiesListToJsonResponse(response, preparedQuery,"Feed");
		} 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Feed feed = (Feed)JsonOperations.jsonToObject(request,"Feed","asSingleEntity");
		
		System.out.println(feed.getFeedText()+" "+feed.getImageUrl()+" "+feed.getFeedId()+" "+feed.getTimeStamp());
		
		DatastoreOperations.ObjectToDatastore(feed, "Feed");
		
	}

}
