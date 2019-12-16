package com.sessionManagament;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.externalOperations.DatastoreOperations;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.model.UserDetail;

/**
 * Servlet implementation class UserData
 */
@WebServlet("/userdata")
public class UserData extends HttpServlet {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		Query q = new Query("UserDetail").addFilter("userId", FilterOperator.EQUAL, session.getAttribute("userId"));
		List<Entity> preparedQuery = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(4));
		UserDetail userDetail = (UserDetail) DatastoreOperations.EntitiesListToObjectList(preparedQuery,"UserDetail","asSingleObject");
		
		ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = mapper.writeValueAsString(userDetail);
		
		response.setContentType("application/json");
		response.getWriter().print(json);
	}
}
