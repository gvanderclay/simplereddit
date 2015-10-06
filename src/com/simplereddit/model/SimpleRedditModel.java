package com.simplereddit.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.simplereddit.Link;
import com.simplereddit.SimpleRedditConstants;

public class SimpleRedditModel {

	/**
	 * Client used to make http requests to the reddit server
	 */
	private final HTTPRequester http = new HTTPRequester();

	/**
	 * Maximum amount of links per page
	 */
	private final int LINKS_PER_PAGE = 25;

	/**
	 * Amount of seconds needed to wait between each request
	 */
	private final int SECONDS_TO_WAIT_PER_REQUEST = 2;

	/**
	 * ArrayList of links that the application will store. Will be cleared
	 * everytime the frontpage, or the frontpage of a subreddit is loaded
	 */
	private ArrayList<Link> links;

	/**
	 * Current index of links being shown I.E. if it is the front page,
	 */
	private int linkCount;

	/**
	 * Link that is currently being viewed
	 */
	private Link currentLink;

	private int currentLinkIndex;

	public SimpleRedditModel() {
		this.links = new ArrayList<Link>();
		this.linkCount = 0;
		writeToHeadersFile("User-Agent:desktop:com.simplereddit:v0.1");
		retrieveFrontPage();
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
	}

	/**
	 * Retrieves the front page of Reddit and puts the links on the front page
	 * into the links ArrayList
	 */
	public void retrieveFrontPage() {
		// clear the links in the links array
		this.links.clear();
		this.linkCount = 0;
		String frontPageJSONString = http.getHttp(
				SimpleRedditConstants.REDDIT_URL,
				SimpleRedditConstants.JSON_PATH, null,
				SimpleRedditConstants.HEADERS_FILE);
		JSONObject page = new JSONObject(frontPageJSONString);
		addPageToLinkArray(page);
	}

	/**
	 * Gets the page after whatever the current page is. If there is not a
	 * current page, get the front page instead
	 */
	public void retrieveNextPage() {
		// if there isn't a current page, get the front page
		if (this.links.isEmpty()) {
			retrieveFrontPage();
			return;
		}
		// Get the last link in the current page
		Link lastLink = this.links.get(links.size() - 1);
		// If the linkcount is not divisible by 25, that means we have
		// previously gone back a page. So we have to adjust accordingly
		if (this.linkCount % LINKS_PER_PAGE == 1) {
			this.linkCount -= 1;
		} else {
			this.linkCount += LINKS_PER_PAGE;
		}
		this.links.clear();

		String lastLinkId = lastLink.getId();
		// format the params to query for the next page
		writeToParamsFile("count:" + linkCount, "after:" + lastLinkId);
		String nextPageJSONString = http.getHttp(
				SimpleRedditConstants.REDDIT_URL,
				SimpleRedditConstants.JSON_PATH,
				SimpleRedditConstants.PARAMS_FILE,
				SimpleRedditConstants.HEADERS_FILE);
		JSONObject page = new JSONObject(nextPageJSONString);
		addPageToLinkArray(page);
	}

	/**
	 * Gets the page that is before the current page. If there is not a current
	 * page, gets the front page instead
	 */
	public void retrievePreviousPage() {
		if (this.links.isEmpty()) {
			retrieveFrontPage();
			return;
		}
		Link firstLink = this.links.get(0);
		// If we are on the first page, we can't get the previous page
		if (this.linkCount == 0 || this.linkCount == 26) {
			System.out.println("CAN'T DO THAT");
			return;
		}
		// If the link count is divisible by 25, we need to adjust
		if (this.linkCount % LINKS_PER_PAGE == 1) {
			this.linkCount -= LINKS_PER_PAGE;
		} else {
			this.linkCount += 1;
		}
		this.links.clear();
		String firstLinkId = firstLink.getId();
		// format the params
		writeToParamsFile("count:" + this.linkCount, "before:" + firstLinkId);
		String prevPageJSONString = http.getHttp(
				SimpleRedditConstants.REDDIT_URL,
				SimpleRedditConstants.JSON_PATH,
				SimpleRedditConstants.PARAMS_FILE,
				SimpleRedditConstants.HEADERS_FILE);
		JSONObject page = new JSONObject(prevPageJSONString);
		addPageToLinkArray(page);
	}

	public Link getNextLink() {
		if (currentLinkIndex >= LINKS_PER_PAGE - 1) {
			currentLinkIndex = -1;
			retrieveNextPage();
		}
		this.currentLink = links.get(++currentLinkIndex);
		return this.currentLink;
	}

	public Link getPreviousLink() {
		if (currentLinkIndex == 0) {
			retrievePreviousPage();
			currentLinkIndex = 1;
		}
		this.currentLink = links.get(--currentLinkIndex);
		return this.currentLink;
	}

	public Link getCurrentLink() {
		return this.currentLink;
	}

	/**
	 * Adds a page of reddit to the link array
	 * 
	 * @param page
	 */
	private void addPageToLinkArray(JSONObject page) {
		JSONArray JSONLinks = page.getJSONObject("data").getJSONArray(
				"children");
		for (int i = 0; i < JSONLinks.length(); i++) {
			JSONObject JSONLink = JSONLinks.getJSONObject(i);
			this.links.add(convertJSONLinkToLink(JSONLink));
		}
	}

	/**
	 * Converts a specific link that is in a JSONObject into a Link
	 * 
	 * @param JSONLink
	 *            Link from reddit that is in JSON
	 * @return the converted Link object
	 */
	private Link convertJSONLinkToLink(JSONObject JSONLink) {
		JSONObject JSONLinkData = JSONLink.getJSONObject("data");
		String title = JSONLinkData.getString("title");
		String url = JSONLinkData.getString("url");
		String permaLink = JSONLinkData.getString("permalink");
		String author = JSONLinkData.getString("author");
		String id = JSONLinkData.getString("name");
		int score = JSONLinkData.getInt("score");
		Link link = new Link(title, url, permaLink, author, id, score);
		return link;
	}

	/**
	 * Gets the links on the current page
	 * 
	 * @return the links on the current page
	 */
	public ArrayList<Link> getLinks() {
		return links;
	}

	/**
	 * Set the links on the current page
	 * 
	 * @param links
	 *            links to set as the current page
	 */
	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}

	/**
	 * Add parameters to the parameters file
	 * 
	 * @param params
	 *            The parameters that will be added in the form
	 *            "paramtitle":"paraminfo" without the quotes
	 */
	private void writeToParamsFile(String... params) {
		StringBuilder sb = new StringBuilder();
		for (String param : params) {
			sb.append(param + "\n");
			writeStringToFile(sb.toString(), SimpleRedditConstants.PARAMS_FILE);
		}
	}

	/**
	 * Add headers for the http request
	 * 
	 * @param headers
	 *            The headers that will be added to the headers file in the form
	 *            "headertitle":"headerinfo" without the quotes
	 */
	private void writeToHeadersFile(String... headers) {
		StringBuilder sb = new StringBuilder();
		for (String header : headers) {
			sb.append(header + "\n");
			writeStringToFile(sb.toString(), SimpleRedditConstants.HEADERS_FILE);
		}
	}

	/**
	 * Writes a string to a file
	 * 
	 * @param stringToWrite
	 *            string to be written to the file
	 * @param file
	 *            file being written to
	 */
	private void writeStringToFile(String stringToWrite, File file) {
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.print(stringToWrite);
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Params file not found");
		}

	}

	/**
	 * Wait a certain amount of seconds Used so we don't go over the maximum
	 * amount of requests per minute
	 * 
	 * @param seconds
	 */
	private void waitSeconds(int seconds) {
		try {
			Thread.sleep(1000 * seconds); // 1000 milliseconds is one second.
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String args[]) {
		SimpleRedditModel mod = new SimpleRedditModel();
		System.out.println(mod.getCurrentLink());
		for (int i = 0; i < 10; i++) {
			System.out.println(mod.getNextLink());
		}
		
		for(int i = 0; i < 1000; i++){
			System.out.println(mod.getPreviousLink());
		}

		// for (int i = 0; i < 10; i++) {
		// System.out.println("GETTING PAGE NUMBER: " + i);
		// mod.retrieveNextPage();
		// mod.waitSeconds(2);
		// }
		//
		// for (int i = 0; i < 5; i++) {
		// System.out.println("GOING BACK " + i + " times");
		// mod.retrievePreviousPage();
		// mod.waitSeconds(2);
		// }
		//
		// for (int i = 0; i < 5; i++) {
		// System.out.println("GOING FORWARD " + i + " times");
		// mod.retrieveNextPage();
		// mod.waitSeconds(2);
		// }

		// System.out.println("GETTING FIRST PAGE");
		// mod.retrieveFrontPage();
		// mod.waitOneSecond();
		// for (Link link : mod.getLinks()) {
		// System.out.println(link + "\n");
		// }
		//
		// System.out.println("GETTING SECOND PAGE");
		// mod.retrieveNextPage();
		// mod.waitOneSecond();
		// for (Link link : mod.getLinks()) {
		// System.out.println(link + "\n");
		// }
		//
		// System.out.println("GETTING FIRST PAGE AGAIN");
		// mod.retrievePreviousPage();
		// mod.waitOneSecond();
		// for (Link link : mod.getLinks()) {
		// System.out.println(link + "\n");
		// }
		//
		// System.out.println("TRYING TO GO BACK ON FIRST PAGE");
		// mod.retrievePreviousPage();
		// mod.waitOneSecond();
		// for (Link link : mod.getLinks()) {
		// System.out.println(link + "\n");
		// }

	}

}
