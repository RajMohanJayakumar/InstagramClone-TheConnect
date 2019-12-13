import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.repackaged.gentyref.TypeToken;

@WebServlet("/datastore")
public class Datastore extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Adding an Entity to a datastore
	
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
//		Key userKey = KeyFactory.createKey("User", "username");
//		System.out.println(userKey);
		
		Entity user = new Entity("User","12");				//Creating an entity with an id "1"
		user.setProperty("Name", "Kaasdmal");
		user.setProperty("email", "raqwedfjmohan@gmail.com");
		
		datastore.put(user);
		
		/*-----------------------------------------------------------------------*/
		
		//Adding a list of entities
		
		List<Entity> list = new ArrayList();
		Entity address = new Entity("address","1");
		address.setProperty("Street","John Street");
		address.setProperty("City", "Chennai");
		list.add(address);
		
		Entity contact1 = new Entity("contact","1");
		contact1.setProperty("Email", "rajmohan1@gmail.com");
		contact1.setProperty("Phone", "9876543210");
		list.add(contact1);
		
		datastore.put(list);		
		
		Query q = new Query("address");
//		PreparedQuery pq = datastore.prepare(q);
		List<Entity> pq = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(500));
		System.out.println(pq.get(0));
//		Contact contact2 = jsonPharse(pq.get(0));
		
		ListToJsonResponse(response,pq);
		
		
//		System.out.println(contact1.getName()+contact1.getEmail());
		
//		public Contact jsonPharse(String str) throws IOException {
//			
//			//Creating reference to store the contact variable
//			Contact contact = null;
//			
//		      ObjectMapper mapper = new ObjectMapper();
//		      
//		      try
//		      {
//		         contact =  mapper.readValue(str.getBytes(),new TypeToken<ArrayList<Contact.class>>{}.getClass());
//		      } catch (JsonGenerationException e)
//		      {
//		         e.printStackTrace();
//		      } catch (JsonMappingException e)
//		      {
//		         e.printStackTrace(); 
//		      } catch (IOException e)
//		      {
//		         e.printStackTrace();
//		      }
//		      return contact;
//		}
		}
	
	public void ListToJsonResponse(HttpServletResponse response,List<Entity> entities) throws JsonGenerationException, JsonMappingException, IOException {
		
		Contact contact = null;
		
		Map<String,Object> properties = new HashMap<>();
	    
		ObjectMapper mapper = new ObjectMapper();
		
		List<Contact> list = new ArrayList<>();
		
		for(Entity entity : entities) { 
			properties = entity.getProperties();
			contact = new Contact(properties.get("phoneNumber"),properties.get("Name"),properties.get("email"));
			list.add(contact);
		}
		String str = mapper.writeValueAsString(list);
		response.setContentType("application/json");
		response.getWriter().print(str);
		System.out.println(list);
	}
	
	public Contact jsonPharse(Entity str) throws IOException {
		
		//Creating reference to store the contact variable
		Contact contact = null;
		
		  Map<String,Object> properties = new HashMap<>();
	      ObjectMapper mapper = new ObjectMapper();
	      
	      System.out.println(str.getKey());
	      System.out.println(str.getProperties());
	      
	      properties = str.getProperties();
	      
	      System.out.println(properties.get("Name"));
	      
	      Contact contact1 = new Contact(properties.get("phoneNumber"),properties.get("Name"),properties.get("email"));
	      
	      System.out.println("Name : "+contact1.getName()+" Email : "+contact1.getEmail()+" PhoneNumber : "+contact1.getPhoneNumber());
//	      try
//	      {
	    	  
//	         System.out.println(mapper.readValue(str, (Class<Contact>) (new TypeToken<ArrayList<Contact>>(){}.getType())));
//	      } catch (JsonGenerationException e)
//	      {
//	         e.printStackTrace();
//	      } catch (JsonMappingException e)
//	      {
//	         e.printStackTrace(); 
//	      } catch (IOException e)
//	      {
//	         e.printStackTrace();
//	      }
//	      System.out.println(contact);
//	      return contact;
	      return null;
	}
}