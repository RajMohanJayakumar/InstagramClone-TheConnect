package com.zOldtheconnect;

import java.util.Arrays;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class Post {

@Id
private String postId;

private String userId;

private String likes[];

private String image;

private String feedText;

private String commentIds[];

private String timeStramp;

private String status;

public String getPostId() {
	return postId;
}

public void setPostId(String postId) {
	this.postId = postId;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String[] getLikes() {
	return likes;
}

public void setLikes(String[] likes) {
	this.likes = likes;
}

public String getImage() {
	return image;
}

public void setImage(String image) {
	this.image = image;
}

public String getFeedText() {
	return feedText;
}

public void setFeedText(String feedText) {
	this.feedText = feedText;
}

public String[] getCommentIds() {
	return commentIds;
}

public void setCommentIds(String[] commentIds) {
	this.commentIds = commentIds;
}

public String getTimeStramp() {
	return timeStramp;
}

public void setTimeStramp(String timeStramp) {
	this.timeStramp = timeStramp;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

@Override
public String toString() {
	return "Post [postId=" + postId + ", userId=" + userId + ", likes=" + Arrays.toString(likes) + ", image=" + image
			+ ", feedText=" + feedText + ", commentIds=" + Arrays.toString(commentIds) + ", timeStramp=" + timeStramp
			+ ", status=" + status + "]";
}


}