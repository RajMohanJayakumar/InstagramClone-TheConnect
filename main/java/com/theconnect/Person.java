package com.theconnect;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Person {
	
	@Id
	@Index
	String name;
	String email;
	public Person(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
}
