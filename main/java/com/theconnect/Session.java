package com.theconnect;

import java.util.Arrays;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class Session {

@Id
@Index
private String session;
private String email;
public String getSession() {
	return session;
}
public void setSession(String session) {
	this.session = session;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}

@Override
public String toString() {
	return "Session [session=" + session + ", email=" + email + "]";
}
}
