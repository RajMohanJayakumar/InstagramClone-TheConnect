package com.externalOperations;

import com.model.*;

import java.util.UUID;

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
		
		case "UserDetails":{
			Entity userDetailEntity = new Entity(entity);
			userDetailEntity.setProperty("userId",((UserDetail) object).getUserId());
			userDetailEntity.setProperty("name", ((UserDetail) object).getName());
			userDetailEntity.setProperty("proPicUrl", ((UserDetail) object).getProPicUrl());
			userDetailEntity.setProperty("email", ((UserDetail) object).getEmail());
			datastore.put(userDetailEntity);
			return;
		}
		
		
	}
	}
}
