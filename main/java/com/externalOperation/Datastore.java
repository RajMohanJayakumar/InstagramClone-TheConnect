package com.externalOperation;

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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Datastore {
	
	static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public static void ObjectToDatastore(Object object,String entity) {
		
		switch(entity) {
		case "Feed":{
			
			Key feedKey = KeyFactory.createKey(entity, ((Feed) object).getFeedId());
			Entity feedEntity = new Entity(entity,((Feed) object).getFeedId());
			feedEntity.setProperty("feedId", ((Feed) object).getFeedId());
			feedEntity.setProperty("userId", ((Feed) object).getUserId());
			feedEntity.setProperty("feedText",((Feed) object).getFeedText());
			feedEntity.setProperty("imageUrl", ((Feed) object).getImageUrl());
			feedEntity.setProperty("timeStamp", ((Feed) object).getTimeStamp());
			feedEntity.setProperty("status", ((Feed) object).getStatus());
			feedEntity.setProperty("edit",((Feed) object).isEdit());
			
			datastore.put(feedEntity);
			return;
		}
		
		case "UserDetail":{
			
			Key userDetailKey = KeyFactory.createKey(entity, ((UserDetail) object).getUserId());
			
			Entity userDetailEntity = new Entity(entity,((UserDetail) object).getUserId());
			userDetailEntity.setProperty("userId", ((UserDetail) object).getUserId());
			userDetailEntity.setProperty("name", ((UserDetail) object).getName());
			userDetailEntity.setProperty("proPicUrl", ((UserDetail) object).getProPicUrl());
			userDetailEntity.setProperty("email", ((UserDetail) object).getEmail());
			datastore.put(userDetailEntity);
			return;
		}
		
		case "SessionLog":{
			Entity sessionLogEntity = new Entity(entity,((SessionLog) object).getSessionId());
			sessionLogEntity.setProperty("key", ((SessionLog) object).getKey());
			sessionLogEntity.setProperty("userId", ((SessionLog) object).getUserId());
			datastore.put(sessionLogEntity);
			return;
		}
		
		case "UserCredential":{
			Entity userCredentialEntity = new Entity(entity,((UserCredential) object).getUsername());
			userCredentialEntity.setProperty("password", ((UserCredential) object).getPassword());
			userCredentialEntity.setProperty("userId", ((UserCredential) object).getUserId());
			datastore.put(userCredentialEntity);
			return;
		}
	}
		return;
	}
	
	public static Object EntitiesListToObjectList(List<Entity> entityList,String classname,String returnAs) throws JsonGenerationException, JsonMappingException, IOException {
		
		List<Object> list = EntitiesListToObjectList(entityList,classname);
		return list.get(0);
	}
	
public static List EntitiesListToObjectList(List<Entity> entities,String classname) throws JsonGenerationException, JsonMappingException, IOException {
		
		Map<String,Object> properties;

		switch(classname) {
		case "Feed" :{
			properties = new HashMap<>();
			List<Feed> list = new ArrayList<Feed>();
			for(Entity entity : entities) { 
				Feed feed = new Feed();
				properties = entity.getProperties();
				if(String.valueOf(entity.getProperty("status")).equals("active")) {
				feed.setFeedId(String.valueOf(entity.getProperty("feedId")));
				feed.setUserId(String.valueOf(entity.getProperty("userId")));
				feed.setFeedText(String.valueOf(entity.getProperty("feedText")));
				feed.setImageUrl(String.valueOf(entity.getProperty("imageUrl")));
				feed.setTimeStamp(Long.valueOf((long) entity.getProperty("timeStamp")));
				feed.setStatus(String.valueOf(entity.getProperty("status")));
				}
				list.add(feed);
			}
			return list;
		}
		
		case "UserDetail" :{
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
		
		case "UserCredential" :{
			properties = new HashMap<>();
			List<UserCredential> list = new ArrayList<UserCredential>();
			for(Entity entity : entities) {
				properties = entity.getProperties();
				UserCredential userCredential = new UserCredential();
					userCredential.setUsername(String.valueOf(entity.getProperty("username")));
					userCredential.setUserId(String.valueOf(entity.getProperty("userId")));
					userCredential.setPassword(String.valueOf(entity.getProperty("password")));
					list.add(userCredential);
				}
			return list;
			}
		}
		return null;
	}
}
