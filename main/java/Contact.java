import java.io.Serializable;

public class Contact implements Serializable{
	private Object phoneNumber;
	private Object email;
	private Object Name;
	
	
	
	public Object getPhoneNumber() {
		return phoneNumber;
	}



	public void setPhoneNumber(Object phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public Object getEmail() {
		return email;
	}



	public void setEmail(Object email) {
		this.email = email;
	}



	public Object getName() {
		return Name;
	}



	public void setName(Object name) {
		Name = name;
	}



	public Contact(Object phoneNumber,Object email, Object name) {
		super();
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.Name = name;
	}
	
}
