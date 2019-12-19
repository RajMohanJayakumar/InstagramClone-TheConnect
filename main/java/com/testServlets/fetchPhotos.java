package com.testServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;

@WebServlet("/fetchphotos")
public class fetchPhotos extends HttpServlet {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Query q = new Query("Feed");
//		q.addProjection(new PropertyProjection("A", String.class));
//		q.addProjection(new PropertyProjection("B", Long.class));
//		q.setDistinct(true);
//		q.setFilter(Query.FilterOperator.LESS_THAN.of("B", 1L));
//		q.addSort("B", Query.SortDirection.DESCENDING);
//		q.addSort("A");
	}


}
