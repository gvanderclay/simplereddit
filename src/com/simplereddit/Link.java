package com.simplereddit;

import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * Class that represents a link in reddit
 * @author Gage and Kevin
 *
 */
public class Link {

	/**
	 * Title of the post
	 */
	private String title;

	/**
	 * URL that relates to this link
	 */
	private String url;

	/**
	 * URL to the Reddit post
	 */
	private String permaLink;

	/**
	 * Author of the post
	 */
	private String author;

	/**
	 * id of the page
	 */
	private String id;

	/**
	 * Amount of karma this link has on reddit
	 */
	private int score;
	
	private String subreddit;
	
	private Date date;
	

	public Link(String title, String url, String permaLink, String author,
			String id, int score, String subreddit, Date date) {
		this.title = title;
		this.url = url;
		this.permaLink = permaLink;
		this.author = author;
		this.id = id;
		this.score = score;
		this.subreddit = subreddit;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermaLink() {
		return permaLink;
	}

	public void setPermaLink(String permaLink) {
		this.permaLink = permaLink;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb, Locale.US);
		formatter.format(
				"TITLE: %s\nURL: %s\nPERMALINK: %s\nAUTHOR: %s\nID: %s\nSCORE: %d",
				title, url, permaLink, author, id, score);
		formatter.close();
		return sb.toString();
	}

	public String getSubreddit() {
		return subreddit;
	}

	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public boolean equals(Link link){
		if((this.title.equals(link.title)) && (this.url.equals(link.url))){
			return true;
		}
		return false;
	}
}
