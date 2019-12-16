package com.sessionManagament;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.externalOperation.DatastoreOperations;
import com.externalOperation.JsonOperations;
import com.model.UserDetail;

@WebServlet("/session")
public class Session extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute("userId") != null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else {
			System.out.println("Session = Null");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserDetail userDetail = (UserDetail) JsonOperations.jsonToObject(request, "UserDetail","asSingleObject");

		if(userDetail.getUserId() == null || userDetail.getEmail() == null || userDetail.getName() == null || userDetail.getProPicUrl() == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
		HttpSession session = request.getSession();
		DatastoreOperations.ObjectToDatastore(userDetail,"UserDetail");
		session.setAttribute("userId", userDetail.getUserId());

	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session != null)
		session.invalidate();
	}

}