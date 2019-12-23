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
import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.externalOperation.*;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.QueryResultList;
import com.model.Feed;
import com.model.UserDetail;

@WebServlet("/feed")
public class FeedsController extends HttpServlet {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	ObjectMapper mapper = new ObjectMapper();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String getFeeds = request.getParameter("getFeeds");
		String fetch = request.getParameter("fetch");
		HttpSession session = request.getSession(false);
		
		if(getFeeds != null)
		switch(getFeeds) {
		case "getAll" : {
//			Map<String, Object> resultMap = new HashMap<>();
//			Query q = new Query("Feed")
//					.addSort("timeStamp", Query.SortDirection.DESCENDING);
//			FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10);
//		    // If this servlet is passed a cursor parameter, let's use it.
//		    String startCursor = request.getParameter("cursor");
//		    if (startCursor != null) {
//		      fetchOptions.cursor(Cursor.fromWebSafeString(startCursor));
//		    }
//			QueryResultList<Entity> preparedQuery = datastore.prepare(q).asQueryResultList(fetchOptions);
//			List<Feed> feeds =  DatastoreOperation.EntitiesListToObjectList(preparedQuery,"Feed");
//			resultMap.put("feeds", feeds);
//			resultMap.put("cursor",preparedQuery.getCursor().toWebSafeString());
//			String json = mapper.writeValueAsString(feeds);
//			response.setContentType("application/json");
//			response.getWriter().print(json);
			
			Query q = new Query("Feed")
					.addSort("timeStamp", Query.SortDirection.DESCENDING);
			List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(100));
			List<Feed> feed = DatastoreOperation.EntitiesListToObjectList(preparedQuery,"Feed");
			String json = mapper.writeValueAsString(feed);
			response.setContentType("application/json");
			response.getWriter().print(json);
		}
		break;
		
		case "getOne" : {
			if(fetch != null) {
			Query q = new Query("Feed").addFilter("feedId", FilterOperator.EQUAL, fetch);
			List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(100));
			List<Feed> feed =  DatastoreOperation.EntitiesListToObjectList(preparedQuery,"Feed");
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
//				Map<String, Object> resultMap = new HashMap<>();
//				Query q = new Query("Feed").addFilter("userId", FilterOperator.EQUAL, fetch);
//				q.addSort("timeStamp", Query.SortDirection.DESCENDING);
//				FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10);
//			    // If this servlet is passed a cursor parameter, let's use it.
//			    String startCursor = request.getParameter("cursor");
//			    if (startCursor != null) {
//			      fetchOptions.cursor(Cursor.fromWebSafeString(startCursor));
//			    }
//				QueryResultList<Entity> preparedQuery = datastore.prepare(q).asQueryResultList(fetchOptions);
//				List<Feed> feeds =  DatastoreOperation.EntitiesListToObjectList(preparedQuery,"Feed");
//				resultMap.put("feeds", feeds);
//				resultMap.put("cursor",preparedQuery.getCursor().toWebSafeString());
//				String json = mapper.writeValueAsString(feeds);
//				response.setContentType("application/json");
//				response.getWriter().print(json);
				
				Query q = new Query("Feed").addFilter("userId", FilterOperator.EQUAL, fetch);
				q.addSort("timeStamp", Query.SortDirection.DESCENDING);
				List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(100));
				List<Feed> feeds =  DatastoreOperation.EntitiesListToObjectList(preparedQuery,"Feed");
				String json = mapper.writeValueAsString(feeds);
				response.setContentType("application/json");
				response.getWriter().print(json);
		}
			break;
		
		default : {
			response.setStatus(404);
			return;
		}
		}
		else
			response.setStatus(400);
			return;
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);

		Feed feed = (Feed)JsonPharsingOperation.jsonToObject(request,"Feed","asSingleObject");
		
		UUID id = UUID.randomUUID();
		
		if(session != null && session.getAttribute("userId") != null)
		feed.setUserId((String)session.getAttribute("userId"));
		
		feed.setFeedId(id.toString());
		feed.setStatus("active");
		feed.setEdited("false");
		
		DatastoreOperation.ObjectToDatastore(feed, "Feed");
		
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		HttpSession session = request.getSession(false);
		
		Feed feed = (Feed)JsonPharsingOperation.jsonToObject(request,"Feed","asSingleObject");
		Query q = new Query("Feed").addFilter("feedId", FilterOperator.EQUAL, feed.getFeedId());
		List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(4));
		Feed lastFeedUpdate = (Feed) DatastoreOperation.EntitiesListToObjectList(preparedQuery,"Feed","asSingleObject");		
		System.out.println(feed.getImageUrl());
		
		if(session.getAttribute("userId").equals(lastFeedUpdate.getUserId())) {
			feed.setEdited("true");
			if(feed.getImageUrl().equals("null"))
				feed.setImageUrl(lastFeedUpdate.getImageUrl());
			feed.setFeedId(lastFeedUpdate.getFeedId());
			feed.setStatus("active");
			feed.setTimeStamp(lastFeedUpdate.getTimeStamp());
			feed.setUserId(lastFeedUpdate.getUserId());
			DatastoreOperation.ObjectToDatastore(feed, "Feed");
			
		}
		else {
			response.setStatus(401);
			return;
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String feedId = request.getParameter("feedId");
		HttpSession session = request.getSession(false);
		
		Query q = new Query("Feed").addFilter("feedId", FilterOperator.EQUAL, feedId);
		List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(500));
		
		Feed feed = (Feed) DatastoreOperation.EntitiesListToObjectList(preparedQuery,"Feed","AsSingleObject");
		
		if(session.getAttribute("userId").equals(feed.getUserId())) {
			feed.setStatus("deleted");
			DatastoreOperation.ObjectToDatastore(feed, "Feed");
			
		}
		else {
			response.setStatus(401);
			return;
		}
		
	}

}
