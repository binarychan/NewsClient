package com.chan.news.model;

import java.util.Date;

public class News {
	
	private Integer id;
	private String title;
	private String summary;
	private int followNum;
	private String content;
	private String newsCategory;
	private String picUrl;
	private String publishTime;
	
	public String getNewsCategory() {
		return newsCategory;
	}

	public void setNewsCategory(String newsCategory) {
		this.newsCategory = newsCategory;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public News() {}
	
	public News(Integer id, String title, String summary, int followNum,
			String content) {
		super();
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.followNum = followNum;
		this.content = content;
	}

	public int getFollowNum() {
		return followNum;
	}

	public void setFollowNum(int followNum) {
		this.followNum = followNum;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", summary=" + summary
				+ ", followNum=" + followNum + ", content=" + content + "]";
	}
	
}
