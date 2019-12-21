package com.testServlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.externalOperation.DatastoreOperation;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.model.Feed;

@WebServlet("/fetchphotos")
public class fetchPhotos extends HttpServlet {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Query q = new Query("Feed").addFilter("userId", FilterOperator.EQUAL, "1014586");
		List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));
//		List<Feed> feeds =  DatastoreOperation.EntitiesListToObjectList(preparedQuery,"Feed");
		System.out.println(preparedQuery);
		if(preparedQuery.isEmpty()) {
			System.out.println("Perfect Null");
		}
	}


}
