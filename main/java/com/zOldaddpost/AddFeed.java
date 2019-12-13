package com.zOldaddpost;

import java.io.Serializable;

public class AddFeed implements Serializable{
	
	private String email;
	private String text;
    private String photo;
    private String timeStramp;
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getTimeStramp() {
		return timeStramp;
	}
	public void setTimeStramp(String timeStramp) {
		this.timeStramp = timeStramp;
	}
}
