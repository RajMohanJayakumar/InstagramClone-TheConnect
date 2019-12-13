package com.zOldtheconnect;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDetails implements Serializable{
	private String id;
	private String name;
	private String imgUrl;
	private String email;
	private ArrayList<String> postIds;  
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

	
	
}
