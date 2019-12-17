package com.controller;

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
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.externalOperation.*;
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

@WebServlet("/feed")
public class Feeds extends HttpServlet {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	ObjectMapper mapper = new ObjectMapper();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String getFeeds = request.getParameter("getFeeds");
		String fetch = request.getParameter("fetch");
		HttpSession session = request.getSession(false);
		
		if(getFeeds != null)
		switch(getFeeds) {
		
		case "getAll" : {
			Query q = new Query("Feed").addSort("timeStamp", Query.SortDirection.DESCENDING);
			List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(500));
			List<Feed> feed = Datastore.EntitiesListToObjectList(preparedQuery,"Feed");
			String json = mapper.writeValueAsString(feed);
			response.setContentType("application/json");
			response.getWriter().print(json);
		}
		break;
		
		case "getOne" : {
			if(fetch != null) {
			Query q = new Query("Feed").addFilter("feedId", FilterOperator.EQUAL, fetch);
			List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(5));
			List<Feed> feed =  Datastore.EntitiesListToObjectList(preparedQuery,"Feed");
			String json = mapper.writeValueAsString(feed);
			response.setContentType("application/json");
			response.getWriter().print(json);
		}
			else {
				response.setStatus(400);
			}
		}
		break;
		
		
		case "getUserFeeds" : {
			if(session != null && session.getAttribute("userId") != null) {
				Query q = new Query("Feed").addFilter("userId", FilterOperator.EQUAL, fetch)
						.addSort("timeStamp", Query.SortDirection.DESCENDING);
				List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(500));
				List<Feed> feeds =  Datastore.EntitiesListToObjectList(preparedQuery,"Feed");
				String json = mapper.writeValueAsString(feeds);
				response.setContentType("application/json");
				response.getWriter().print(json);
		}
			break;
			
		}
		
		default : {
			response.setStatus(404);
			return;
		}
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);

		Feed feed = (Feed)JsonPharsing.jsonToObject(request,"Feed","asSingleObject");
		
		if(session != null && session.getAttribute("userId") != null)
		feed.setUserId((String)session.getAttribute("userId"));
		
		Datastore.ObjectToDatastore(feed, "Feed");
		
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String feedId = request.getParameter("feedId");
		HttpSession session = request.getSession(false);
		
		Query q = new Query("Feed").addFilter("feedId", FilterOperator.EQUAL, feedId);
		List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(500));
		
		Feed feed = (Feed) Datastore.EntitiesListToObjectList(preparedQuery,"Feed","AsSingleObject");
		
		if(session.getAttribute("userId").equals(feed.getUserId())) {
		
			//Delete function
//			Key feedKey = KeyFactory.createKey("Feed", id.toString());
			System.out.println(feedId);
			
		}
		else {
			response.setStatus(401);
			return;
		}
		
	}

}
