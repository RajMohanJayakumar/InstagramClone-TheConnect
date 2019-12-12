
public class Contact {
	private String email;
	private String Name;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Contact(String email, String name) {
		super();
		this.email = email;
		Name = name;
	}
	
}
