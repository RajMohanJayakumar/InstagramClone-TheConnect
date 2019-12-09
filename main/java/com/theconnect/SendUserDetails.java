package com.theconnect;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

/**
 * Servlet implementation class SendUserDetails
 */
@WebServlet("/senduserdetails")
public class SendUserDetails extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		SendUserDetailsPojo sendUserDetails = new SendUserDetailsPojo("Raj Mohan","rajmohan2695@gmail.com","https://lh3.googleusercontent.com/a-/AAuE7mDZAGHznLCeuzmH9qFQeYhzXCPTfUMjL1dit8wmOs0=s96-c");
		
		String json = ow.writeValueAsString(sendUserDetails);
		response.setContentType("application/json");
		response.getWriter().print(json);
		System.out.println("getOne Successful");
		
	}
	

}
