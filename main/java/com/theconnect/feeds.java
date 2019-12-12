package com.theconnect;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.model.Feed;

/**
 * Servlet implementation class feeds
 */
@WebServlet("/feeds")
public class feeds extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Feed feed = new Feed("Raj Mohan","https://upload.wikimedia.org/wikipedia/commons/4/42/Shaqi_jrvej.jpg","Hi..Hello","https://upload.wikimedia.org/wikipedia/commons/4/42/Shaqi_jrvej.jpg",121,21,157612942182545L);
		
		String json = ow.writeValueAsString(feed);
		response.setContentType("application/json");
		response.getWriter().print(json);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/feed post");
	}

}
