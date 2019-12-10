package com.userdetails;

public class SendUserDetailsPojo {
	private String username;
	private String email;
	private String imgUrl;
	
	
	
	public SendUserDetailsPojo(String username, String email, String imgUrl) {
		super();
		this.username = username;
		this.email = email;
		this.imgUrl = imgUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
