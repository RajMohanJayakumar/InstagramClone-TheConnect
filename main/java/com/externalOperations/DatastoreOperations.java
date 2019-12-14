package com.externalOperations;

import com.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class DatastoreOperations {
	
	static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	public static void ObjectToDatastore(Object object,String entity) {
		
		

//		Key taskKey = datastore.newKeyFactory()
//		.setKind("Feed")
//		.newKey("FeedId");
//Entity user = new Entity(taskKey);
		
		switch(entity) {
		case "Feed":{
			UUID id = UUID.randomUUID();
			Entity feedEntity = new Entity(entity);
			feedEntity.setProperty("feedId", id.toString());
			feedEntity.setProperty("userId", ((Feed) object).getUserId());
			feedEntity.setProperty("feedText",((Feed) object).getFeedText());
			feedEntity.setProperty("imageUrl", ((Feed) object).getImageUrl());
			feedEntity.setProperty("timeStamp", ((Feed) object).getTimeStamp());
			feedEntity.setProperty("status", ((Feed) object).getStatus());
			datastore.put(feedEntity);
			return;
		}
		
		case "UserDetail":{
			Entity userDetailEntity = new Entity(entity);
			userDetailEntity.setProperty("userId",((UserDetail) object).getUserId());
			userDetailEntity.setProperty("name", ((UserDetail) object).getName());
			userDetailEntity.setProperty("proPicUrl", ((UserDetail) object).getProPicUrl());
			userDetailEntity.setProperty("email", ((UserDetail) object).getEmail());
			datastore.put(userDetailEntity);
			return;
		}
		
		case "SessionLog":{
			Entity sessionLogEntity = new Entity(entity);
			sessionLogEntity.setProperty("sessionId", ((SessionLog) object).getSessionId());
			sessionLogEntity.setProperty("key", ((SessionLog) object).getKey());
			sessionLogEntity.setProperty("userId", ((SessionLog) object).getUserId());
			datastore.put(sessionLogEntity);
			return;
		}
	}
	}
	
public static List EntitiesListToObjectList(HttpServletResponse response,List<Entity> entities,String classname) throws JsonGenerationException, JsonMappingException, IOException {
		
		Map<String,Object> properties;

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
			return list;
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
			return list;
			}
		case "SessionLog" :{
			properties = new HashMap<>();
			List<SessionLog> list = new ArrayList<SessionLog>();
			for(Entity entity : entities) {
				properties = entity.getProperties();
				SessionLog sessionLog = new SessionLog();
					sessionLog.setSessionId(String.valueOf(entity.getProperty("sessionId")));
					sessionLog.setKey(String.valueOf(entity.getProperty("key")));
					sessionLog.setUserId(String.valueOf(entity.getProperty("userId")));
					list.add(sessionLog);
				}
			return list;
			}
		}
		return null;
	}
}
