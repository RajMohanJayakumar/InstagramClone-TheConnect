package com.externalOperations;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.appengine.api.datastore.Entity;
import com.model.Feed;
import com.model.UserDetail;

public class JsonOperations {
	
	public static Object jsonToObject(HttpServletRequest request,String classname,String returnAs) throws IOException {
		 List<Object> obj = (List<Object>) jsonToObject(request,classname);
		 if(returnAs == "asSingleEntity")
		 return obj.get(0);
		 return obj;
	}
	
	public static List jsonToObject(HttpServletRequest request,String classname) throws IOException {
		
		String line = "";
		StringBuffer str = new StringBuffer();
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null)
			{
			str.append(line); 
			}
		String jsonString = str.toString();

	    ObjectMapper mapper = new ObjectMapper();
	    
	    switch(classname) {
	    	case "Feed" : List<Feed> feed = mapper.readValue(jsonString, new TypeReference<List<Feed>>() {}); return feed; 
	    	case "UserDetail" : List<UserDetail> userdetail = mapper.readValue(jsonString, new TypeReference<List<UserDetail>>() {});return userdetail;
	    }
	    return null;
	}
	
public static void EntitiesListToJsonResponse(HttpServletResponse response,List<Entity> entities,String classname) throws JsonGenerationException, JsonMappingException, IOException {
		
		Map<String,Object> properties;
	    
		ObjectMapper mapper = new ObjectMapper();
		
		String json = null;

		switch(classname) {
		case "Feed" :{
			properties = new HashMap<>();
			List<Feed> list = new ArrayList<Feed>();
			for(Entity entity : entities) { 
				properties = entity.getProperties();
				Feed feed = new Feed();
				feed.setFeedId(String.valueOf(entity.getProperty("feedId")));
				feed.setUserId(String.valueOf(entity.getProperty("userId")));
				feed.setFeedText(String.valueOf(entity.getProperty("feedText")));
				feed.setImageUrl(String.valueOf(entity.getProperty("imageUrl")));
				feed.setTimeStamp(String.valueOf(entity.getProperty("timeStamp")));
				feed.setStatus(String.valueOf(entity.getProperty("status")));
				list.add(feed);
			}
			json = mapper.writeValueAsString(list);
			break;
		}
		case "UserDetails" :{
			properties = new HashMap<>();
			List<UserDetail> list = new ArrayList<UserDetail>();
			for(Entity entity : entities) {
				properties = entity.getProperties();
				UserDetail userDetail = new UserDetail();
					userDetail.setUserId(String.valueOf(entity.getProperty("userId")));
					userDetail.setName(String.valueOf(entity.getProperty("name")));
					userDetail.setProPicUrl(String.valueOf(entity.getProperty("proPicUrl")));
					userDetail.setEmail(String.valueOf(entity.getProperty("email")));
					list.add(userDetail);
				}
			json = mapper.writeValueAsString(list);
			break;
			}
		}
		response.setContentType("application/json");
		response.getWriter().print(json);
	}
	
}