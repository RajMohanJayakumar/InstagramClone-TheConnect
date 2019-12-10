package com.addpost;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet implementation class AddPost
 */
@WebServlet("/addpost")
public class AddPost extends HttpServlet {

	AddFeed addfeed;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String str = jsonToString(request);
		addfeed = jsonPharse(str);
		System.out.println(addfeed.getEmail()+addfeed.getPhoto()+addfeed.getText()+addfeed.getTimeStramp());
		
	}
	
	public String jsonToString(HttpServletRequest request) throws IOException {
		String line = "";
		StringBuffer str = new StringBuffer();
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null)
			{
			str.append(line);
			}
		String jsonString = str.toString();
		return jsonString;
	}
	
	//Pharsing a json standard string format to an Object(Contact)
	public AddFeed jsonPharse(String str) throws IOException {
		
		//Creating reference to store the contact variable
		AddFeed addfeed = null;
		
	      ObjectMapper mapper = new ObjectMapper();
	      
	      try
	      {
	         addfeed =  mapper.readValue(str.getBytes(),AddFeed.class);
	      } catch (JsonGenerationException e)
	      {
	         e.printStackTrace();
	      } catch (JsonMappingException e)
	      {
	         e.printStackTrace(); 
	      } catch (IOException e)
	      {
	         e.printStackTrace();
	      }
	      return addfeed;
	}

}
