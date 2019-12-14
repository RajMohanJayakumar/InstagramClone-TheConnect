package com.feed;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.model.Feed;

@WebServlet("/feed")
public class FeedOperations extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Feed Post");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
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
		user.setProperty("timestamp", feed.getTimeStamp());
		user.setProperty("status", feed.getStatus());
		
		datastore.put(user);
		
		
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
