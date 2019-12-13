import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		
		Entity contact = new Entity("contact","1");
		contact.setProperty("Email", "rajmohan1@gmail.com");
		contact.setProperty("Phone", "9876543210");
		list.add(contact);
		
		datastore.put(list);		
		
		
		Query q = new Query("User");
//		PreparedQuery pq = datastore.prepare(q);
		List<Entity> pq = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(500));
		System.out.println(pq);
		Contact contact1 = jsonPharse(pq.toString());
//		System.out.println(contact1.getName()+contact1.getEmail());
		
		
		
		
		
		
		
//public Contact jsonPharse(String str) throws IOException {
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
	
	public Contact jsonPharse(String str) throws IOException {
		
		//Creating reference to store the contact variable
		Contact contact = null;
		
	      ObjectMapper mapper = new ObjectMapper();
	      
	      try
	      {
	         System.out.println(mapper.readValue(str, (Class<Contact>) (new TypeToken<ArrayList<Contact>>(){}.getType())));
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
//	      System.out.println(contact);
//	      return contact;
	      return null;
	}
}
