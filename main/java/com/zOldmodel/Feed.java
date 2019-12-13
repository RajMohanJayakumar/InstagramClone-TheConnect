package com.zOldmodel;

public class Feed {
	private String name;
	private String proImgUrl;
	private String feedText;
	private String feedImgUrl;
	private int likesCount;
	private int commentsCount;
	private long timeStamp;
	
	public Feed(String name, String proImgUrl, String feedText, String feedImgUrl, int likesCount, int commentsCount,
			long timeStamp) {
		super();
		this.name = name;
		this.proImgUrl = proImgUrl;
		this.feedText = feedText;
		this.feedImgUrl = feedImgUrl;
		this.likesCount = likesCount;
		this.commentsCount = commentsCount;
		this.timeStamp = timeStamp;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProImgUrl() {
		return proImgUrl;
	}
	public void setProImgUrl(String proImgUrl) {
		this.proImgUrl = proImgUrl;
	}
	public String getFeedText() {
		return feedText;
	}
	public void setFeedText(String feedText) {
		this.feedText = feedText;
	}
	public String getFeedImgUrl() {
		return feedImgUrl;
	}
	public void setFeedImgUrl(String feedImgUrl) {
		this.feedImgUrl = feedImgUrl;
	}
	public int getLikesCount() {
		return likesCount;
	}
	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}
	public int getCommentsCount() {
		return commentsCount;
	}
	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
