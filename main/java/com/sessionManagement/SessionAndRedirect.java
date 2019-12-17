package com.sessionManagement;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.externalOperation.Datastore;
import com.externalOperation.JsonPharsing;
import com.model.UserDetail;

@WebServlet("/session")
public class SessionAndRedirect extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute("userId") != null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserDetail userDetail = (UserDetail) JsonPharsing.jsonToObject(request, "UserDetail","asSingleObject");

		if(userDetail.getUserId() == null || userDetail.getEmail() == null || userDetail.getName() == null || userDetail.getProPicUrl() == null) {
			response.setStatus(400);
			return;
		}
		
		HttpSession session = request.getSession();
		Datastore.ObjectToDatastore(userDetail,"UserDetail");
		session.setAttribute("userId", userDetail.getUserId());
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session != null)
		session.invalidate();
	}

}