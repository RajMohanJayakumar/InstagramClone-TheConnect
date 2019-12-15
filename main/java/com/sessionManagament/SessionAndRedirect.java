package com.sessionManagament;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.externalOperations.DatastoreOperations;
import com.externalOperations.JsonOperations;
import com.model.UserDetail;

@WebServlet("/redirect")
public class SessionAndRedirect extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/index").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserDetail userDetail = (UserDetail) JsonOperations.jsonToObject(request, "UserDetail");
		DatastoreOperations.ObjectToDatastore(userDetail,"UserDetail");
		session.setAttribute("userId", userDetail.getUserId());
		request.getRequestDispatcher("/index").forward(request, response);
		
	}

}