package com.feed;

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
			
		}
		else {
			Query q = new Query("Feed").addFilter("userId", FilterOperator.EQUAL, userId);
			List<Entity> pq = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(500));
			System.out.println(pq);
			ListToJsonResponse(response, pq);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Feed Post");
		
		
		UUID id = UUID.randomUUID();
		
		String str = jsonToString(request);
		
		Feed feed = jsonPharse(str);
		
		System.out.println(feed.getFeedText()+" "+feed.getImageUrl()+" "+feed.getFeedId()+" "+feed.getTimeStamp());
		
//		Key taskKey = datastore.newKeyFactory()
//				.setKind("Feed")
//				.newKey("FeedId");
//		Entity user = new Entity(taskKey);
		
		Entity user = new Entity("Feed");
		
		user.setProperty("feedId", id.toString());
		user.setProperty("userId", feed.getUserId());
		user.setProperty("feedText",feed.getFeedText());
		user.setProperty("imageUrl", feed.getImageUrl());
		user.setProperty("timeStamp", feed.getTimeStamp());
		user.setProperty("status", feed.getStatus());
		
		datastore.put(user);
	}
	
	public void ListToJsonResponse(HttpServletResponse response,List<Entity> entities) throws JsonGenerationException, JsonMappingException, IOException {
		
		Feed feed = null;
		
		Map<String,Object> properties = new HashMap<>();
	    
		ObjectMapper mapper = new ObjectMapper();
		
		List<Feed> list = new ArrayList<>();
		
		for(Entity entity : entities) { 
			properties = entity.getProperties();
			feed = new Feed();
			feed.setFeedId(String.valueOf(entity.getProperty("feedId")));
			feed.setUserId(String.valueOf(entity.getProperty("userId")));
			feed.setFeedText(String.valueOf(entity.getProperty("feedText")));
			feed.setImageUrl(String.valueOf(entity.getProperty("imageUrl")));
			feed.setTimeStamp(String.valueOf(entity.getProperty("timeStamp")));
			feed.setStatus(String.valueOf(entity.getProperty("status")));
			list.add(feed);
		}
		String str = mapper.writeValueAsString(list);
		response.setContentType("application/json");
		response.getWriter().print(str);
		System.out.println(list);
	}
	
	public String jsonToString(HttpServletRequest request) throws IOException {
		String line = "";
		StringBuffer str = new StringBuffer();
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null)
			{
			str.append(line); 
			}
		String jsonString = str.toString();
//		System.out.println(jsonString);
		return jsonString;
	}
	
	//Pharsing a json standard string format to an Object(Contact)
	public Feed jsonPharse(String str) throws IOException {
	    ObjectMapper mapper = new ObjectMapper();
		List<Feed> feed = mapper.readValue(str, new TypeReference<List<Feed>>() {});
		System.out.println(feed.get(0));
		return feed.get(0);
	}

}
