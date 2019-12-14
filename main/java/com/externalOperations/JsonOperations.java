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
import com.model.*;

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
	    	case "UserDetail" : List<UserDetail> userDetail = mapper.readValue(jsonString, new TypeReference<List<UserDetail>>() {});return userDetail;
	    	case "SessionLog" : List<UserDetail> sessionLog = mapper.readValue(jsonString, new TypeReference<List<UserDetail>>() {});return sessionLog;
	    }
	    return null;
	}
	

	
}