package com.model;

import java.io.Serializable;
import java.util.Arrays;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

public class Feed implements Serializable{
	
private String feedId;

private String userId;

private String imageUrl;

private String feedText;

private String timeStamp;

private String status;

public String getFeedId() {
	return feedId;
}

public void setFeedId(String feedId) {
	this.feedId = feedId;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getImageUrl() {
	return imageUrl;
}

public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}

public String getFeedText() {
	return feedText;
}

public void setFeedText(String feedText) {
	this.feedText = feedText;
}

public String getTimeStamp() {
	return timeStamp;
}

public void setTimeStamp(String timeStamp) {
	this.timeStamp = timeStamp;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

//public Feed(String feedId, String userId, String image, String feedText, String timeStamp, String status) {
//	this.feedId = feedId;
//	this.userId = userId;
//	this.image = image;
//	this.feedText = feedText;
//	this.timeStamp = timeStamp;
//	this.status = status;
//}




}