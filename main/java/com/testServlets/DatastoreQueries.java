package com.testServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.externalOperation.*;
import com.model.UserDetail;

@WebServlet("/DatastoreQueries")
public class DatastoreQueries extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDetail userDetail = new UserDetail();
		
		userDetail.setName("Kalai");
		userDetail.setEmail("kalai@gmail.com");
		userDetail.setUserId("12345");
		userDetail.setProPicUrl("https://lh3.googleusercontent.com/a-/AAuE7mDZAGHznLCeuzmH9qFQeYhzXCPTfUMjL1dit8wmOs0=s96-c");
		
		DatastoreOperations.ObjectToDatastore(userDetail,"UserDetail");
	}
}