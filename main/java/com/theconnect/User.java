package com.theconnect;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class User {

@Id
@Index
private String userId;

private String postIds[];

private String proPic;

private String email;

private String status;


public String getUserId() {
	return userId;
}


public void setUserid(String userid) {
	this.userId = userid;
}


public String[] getPostIds() {
	return postIds;
}


public void setPostIds(String[] postIds) {
	this.postIds = postIds;
}


public String getProPic() {
	return proPic;
}


public void setProPic(String proPic) {
	this.proPic = proPic;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}


public String getStatus() {
	return status;
}


public void setStatus(String status) {
	this.status = status;
}


@Override
public String toString()
{
return "DataStoreObject [userId=" + userId + ", postIds=" + postIds + ", email=" + email + ", status="
+ status + "]";
}

}