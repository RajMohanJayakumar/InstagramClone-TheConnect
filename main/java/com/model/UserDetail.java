package com.model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDetail implements Serializable{
	private String userId;
	private String name;
	private String proPicUrl;
	private String email;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProPicUrl() {
		return proPicUrl;
	}
	public void setProPicUrl(String proPicUrl) {
		this.proPicUrl = proPicUrl;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}