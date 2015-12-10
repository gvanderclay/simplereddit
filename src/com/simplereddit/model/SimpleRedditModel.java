package com.simplereddit.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.simplereddit.Link;
import com.simplereddit.SimpleRedditConstants;

/**
 * Model for the simplereddit application Basically navigates through reddit
 * through manual HTTP requests Data in here is then pulled to the View class
 * and then displayed
 * 
 * @author Gage and Kevin
 *
 */
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
	 * Subreddit that is currently being browsed, when no subreddit is being
	 * browsed it is set to ""
	 */
	private String currentSubreddit;

	/**
	 * Current path of the url
	 */
	private String currentPath;

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

	/**
	 * Index of the currentLink in the links array
	 */
	private int currentLinkIndex;

	/**
	 * Arraylist of saved links
	 */
	private ArrayList<Link> savedLinks;

	/**
	 * Arraylist of strings of subreddits that will be viewed
	 */
	private ArrayList<String> customRedditList;

	/**
	 * If we are browsing the top links of the hour or not
	 */
	private boolean topHour;

	/**
	 * If we are browsing top links of the day or not
	 */
	private boolean topDay;

	/**
	 * If we are browsing top links of the week or not
	 */
	private boolean topWeek;

	/**
	 * If we are browsing the top links of the month or not
	 */
	private boolean topMonth;

	/**
	 * If we are browsing the top links of the year or not
	 */
	private boolean topYear;

	/**
	 * If we are browisng the top links of all time or not
	 */
	private boolean topAll;

	public SimpleRedditModel() {
		this.links = new ArrayList<Link>();
		this.linkCount = 0;
		this.savedLinks = new ArrayList<Link>();
		this.customRedditList = new ArrayList<String>();
		setAllTopSortsToFalse();
		writeToHeadersFile("User-Agent:desktop:com.simplereddit:v0.1");
		retrieveFrontPage();
		currentSubreddit = "";
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
	}

	public static void main(String args[]) {
	}

	/**
	 * Retrieves the front page of Reddit and puts the links on the front page
	 * into the links ArrayList
	 */
	public void retrieveFrontPage() {
		// clear the links in the links array
		resetLinks();
		setAllTopSortsToFalse();
		currentSubreddit = "";
		currentPath = "";
		String frontPageJSONString = retrievePageWithoutParams();
		JSONObject page = new JSONObject(frontPageJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
		System.out.println("getting front page");
	}

	/**
	 * Retrieve a specific subreddit
	 *
	 * @param subreddit
	 *            name of the subreddit I'm going to
	 */
	public void retrieveSubreddit(String subreddit) {
		resetLinks();
		setAllTopSortsToFalse();
		currentSubreddit = subreddit;
		currentPath = "/r/" + subreddit;
		String subredditJSONString = retrievePageWithoutParams();
		JSONObject page = new JSONObject(subredditJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
		System.out.println("Getting subreddit: " + subreddit);
	}

	/**
	 * Get a random subreddit
	 */
	public void getRandomSubreddit() {
		resetLinks();
		setAllTopSortsToFalse();
		currentPath = "/r/random";
		String randomSubredditJSONString = retrievePageWithoutParams();
		JSONObject page = new JSONObject(randomSubredditJSONString);
		currentSubreddit = page.getJSONObject("data").getJSONArray("children").getJSONObject(0).getJSONObject("data")
				.getString("subreddit");
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
		System.out.println("Got random subreddit");
	}

	public String getCurrentSubreddit() {
		return currentSubreddit;
	}

	/**
	 * Get the hot links of the current subreddit or front page
	 */
	public void getHotLinks() {
		resetLinks();
		setAllTopSortsToFalse();
		if (currentSubreddit == "") {
			currentPath = "";
		} else {
			currentPath = "/r/" + currentSubreddit;
		}
		String hotJSONString = retrievePageWithoutParams();
		JSONObject page = new JSONObject(hotJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
	}

	/**
	 * Switching to viewing saved links from normal links
	 */

	public void switchToSavedLinks() {
		if (savedLinks.isEmpty()) {
		} else {
			currentLinkIndex = 0;
			currentLink = savedLinks.get(currentLinkIndex);
		}
	}

	/**
	 * Gets the previous saved link
	 */

	public void getPrevSavedLink() {
		if (currentLinkIndex == 0) {
		} else {
			currentLinkIndex--;
			currentLink = savedLinks.get(currentLinkIndex);
		}
	}

	/**
	 * Gets the next saved link
	 */

	public void getNextSavedLink() {
		if (currentLinkIndex >= savedLinks.size() - 1) {
		} else {
			currentLinkIndex++;
			currentLink = savedLinks.get(currentLinkIndex);
		}
	}

	/**
	 * Gets the links in /r/all
	 */
	public void getAllLinks() {
		resetLinks();
		setAllTopSortsToFalse();
		currentSubreddit = "all";
		currentPath = "/r/" + currentSubreddit;
		String allJSONString = retrievePageWithoutParams();
		JSONObject page = new JSONObject(allJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
	}

	/**
	 * Gets the new links of the current page
	 */
	public void getNewLinks() {
		resetLinks();
		setAllTopSortsToFalse();
		if (currentSubreddit == "") {
			currentPath = "/new";
		} else {
			currentPath = "/r/" + currentSubreddit + "/new";
		}
		String newJSONString = retrievePageWithoutParams();
		JSONObject page = new JSONObject(newJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
	}

	/**
	 * Gets the top links of the hour of the current page
	 */
	public void getTopHourLinks() {
		resetLinks();
		setAllTopSortsToFalse();
		this.topHour = true;
		if (currentSubreddit == "") {
			currentPath = "/top";
		} else {
			currentPath = "/r/" + currentSubreddit + "/top";
		}
		writeStringsToFile(false, SimpleRedditConstants.PARAMS_FILE, new String[0]);
		writeTopSortToParams();
		String topJSONString = retrievePageWithParams();
		JSONObject page = new JSONObject(topJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
	}

	/**
	 * Gets the top links of the day of the current page
	 */
	public void getTopDayLinks() {
		resetLinks();
		setAllTopSortsToFalse();
		this.topDay = true;
		if (currentSubreddit == "") {
			currentPath = "/top";
		} else {
			currentPath = "/r/" + currentSubreddit + "/top";
		}
		writeStringsToFile(false, SimpleRedditConstants.PARAMS_FILE, new String[0]);
		writeTopSortToParams();
		String topJSONString = retrievePageWithParams();
		JSONObject page = new JSONObject(topJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
	}

	/**
	 * Gets the top links of the week of the current page
	 */
	public void getTopWeekLinks() {
		resetLinks();
		setAllTopSortsToFalse();
		this.topWeek = true;
		if (currentSubreddit == "") {
			currentPath = "/top";
		} else {
			currentPath = "/r/" + currentSubreddit + "/top";
		}
		writeStringsToFile(false, SimpleRedditConstants.PARAMS_FILE, new String[0]);
		writeTopSortToParams();
		String topJSONString = retrievePageWithParams();
		JSONObject page = new JSONObject(topJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
	}

	/**
	 * Gets the top links of the month of the current page
	 */
	public void getTopMonthLinks() {
		resetLinks();
		setAllTopSortsToFalse();
		this.topMonth = true;
		if (currentSubreddit == "") {
			currentPath = "/top";
		} else {
			currentPath = "/r/" + currentSubreddit + "/top";
		}
		writeStringsToFile(false, SimpleRedditConstants.PARAMS_FILE, new String[0]);
		writeTopSortToParams();
		String topJSONString = retrievePageWithParams();
		JSONObject page = new JSONObject(topJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
	}

	/**
	 * Gets the top links of the year of the current page
	 */
	public void getTopYearLinks() {
		resetLinks();
		setAllTopSortsToFalse();
		this.topYear = true;
		if (currentSubreddit == "") {
			currentPath = "/top";
		} else {
			currentPath = "/r/" + currentSubreddit + "/top";
		}
		writeStringsToFile(false, SimpleRedditConstants.PARAMS_FILE, new String[0]);
		writeTopSortToParams();
		String topJSONString = retrievePageWithParams();
		JSONObject page = new JSONObject(topJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
	}

	/**
	 * Get the top links of all times of the current page
	 */
	public void getTopAllLinks() {
		resetLinks();
		setAllTopSortsToFalse();
		this.topAll = true;
		if (currentSubreddit == "") {
			currentPath = "/top";
		} else {
			currentPath = "/r/" + currentSubreddit + "/top";
		}
		writeStringsToFile(false, SimpleRedditConstants.PARAMS_FILE, new String[0]);
		writeTopSortToParams();
		String topJSONString = retrievePageWithParams();
		JSONObject page = new JSONObject(topJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
	}

	/**
	 * Clears all the links and resets the linkcount
	 */
	private void resetLinks() {
		this.links.clear();
		this.linkCount = 0;

	}

	/**
	 * Writes the params for the write top sort
	 */
	private void writeTopSortToParams() {
		if (topHour) {
			writeStringsToFile(true, SimpleRedditConstants.PARAMS_FILE, "sort:top", "t:hour");
		} else if (topDay) {
			writeStringsToFile(true, SimpleRedditConstants.PARAMS_FILE, "sort:top", "t:day");
		} else if (topWeek) {
			writeStringsToFile(true, SimpleRedditConstants.PARAMS_FILE, "sort:top", "t:week");
		} else if (topMonth) {
			writeStringsToFile(true, SimpleRedditConstants.PARAMS_FILE, "sort:top", "t:month");
		} else if (topYear) {
			writeStringsToFile(true, SimpleRedditConstants.PARAMS_FILE, "sort:top", "t:year");
		} else if (topAll) {
			writeStringsToFile(true, SimpleRedditConstants.PARAMS_FILE, "sort:top", "t:all");
		}

	}

	/**
	 * Sets all booleans of all types of top sorts to false
	 */
	private void setAllTopSortsToFalse() {
		this.topHour = false;
		this.topDay = false;
		this.topWeek = false;
		this.topMonth = false;
		this.topYear = false;
		this.topAll = false;
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
		writeTopSortToParams();
		String nextPageJSONString = retrievePageWithParams();
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
		writeTopSortToParams();
		String prevPageJSONString = retrievePageWithParams();
		JSONObject page = new JSONObject(prevPageJSONString);
		addPageToLinkArray(page);
	}

	/**
	 * Gets the next link in the arraylist of links and sets the current link to
	 * the next one
	 * 
	 * @return the next link in the list
	 */
	public Link getNextLink() {
		if (currentLinkIndex >= links.size() - 1 && links.size() < LINKS_PER_PAGE) {
			currentLinkIndex = -1;
		}
		if (currentLinkIndex >= LINKS_PER_PAGE - 1) {
			currentLinkIndex = -1;
			retrieveNextPage();
		}
		this.currentLink = links.get(++currentLinkIndex);
		return this.currentLink;
	}

	/**
	 * Gets the previous link in the arraylist of links and sets the current
	 * link to the previous one
	 * 
	 * @return
	 */
	public Link getPreviousLink() {
		System.out.println(atFirstLink());
		if (atFirstLink()) {
			System.out.println("CAN'T DO THAT");
			return this.currentLink;
		}
		if (currentLinkIndex == 0) {
			retrievePreviousPage();
			currentLinkIndex = 25;
		}
		this.currentLink = links.get(--currentLinkIndex);
		return this.currentLink;
	}

	/**
	 * Says if we are at the first link of the subreddit
	 * 
	 * @return if we are at the first link of the subreddit
	 */
	public boolean atFirstLink() {
		if (this.currentLinkIndex == 0 && (this.linkCount == 0 || this.linkCount == 26)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the current link
	 * 
	 * @return the current link
	 */
	public Link getCurrentLink() {
		return this.currentLink;
	}

	/**
	 * Saves the current link of the page
	 */
	public void saveCurrentLink() {
		savedLinks.add(currentLink);
	}

	/**
	 * Clear the saved links
	 */
	public void clearSavedLinks() {
		savedLinks.clear();
	}

	/**
	 * Save the current link to the list of saved subreddits
	 */
	public void saveCurrentSubreddit() {
		if (customRedditList.contains(currentLink.getSubreddit())) {

		} else {
			customRedditList.add(currentLink.getSubreddit());
		}
	}

	/**
	 * Clear the custom subreddits from the array
	 */
	public void clearCustomSubreddits() {
		customRedditList.clear();
	}

	/**
	 * Set the custom subreddits as the current subreddit and retrieve them
	 */
	public void retrieveCustomSubreddit() {
		if (customRedditList.size() == 0) {
			retrieveFrontPage();
			return;
		}
		resetLinks();
		setAllTopSortsToFalse();
		String subreddit = "";
		for (int i = 0; i < customRedditList.size(); i++) {
			subreddit += customRedditList.get(i);
			if (i != customRedditList.size() - 1) {
				subreddit += "+";
			}
		}
		currentSubreddit = subreddit;
		currentPath = "/r/" + subreddit;
		String subredditJSONString = retrievePageWithoutParams();
		JSONObject page = new JSONObject(subredditJSONString);
		addPageToLinkArray(page);
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
		System.out.println("Getting subreddit: " + subreddit);
	}

	/**
	 * Returns the list of saved links
	 */
	public ArrayList<Link> getSavedLinks() {
		return this.savedLinks;
	}

	/**
	 * Adds a page of reddit to the link array
	 *
	 * @param page
	 */
	private void addPageToLinkArray(JSONObject page) {
		JSONArray JSONLinks = page.getJSONObject("data").getJSONArray("children");
		for (int i = 0; i < JSONLinks.length(); i++) {
			JSONObject JSONLink = JSONLinks.getJSONObject(i);
			this.links.add(convertJSONLinkToLink(JSONLink));
		}
	}

	/**
	 * Converts a specific link that is in a JSONObject into a Link
	 *
	 * @param jsonLink
	 *            Link from reddit that is in JSON
	 * @return the converted Link object
	 */
	private Link convertJSONLinkToLink(JSONObject jsonLink) {
		JSONObject jsonLinkData = jsonLink.getJSONObject("data");
		String title = jsonLinkData.getString("title");
		String url = jsonLinkData.getString("url");
		String permaLink = jsonLinkData.getString("permalink");
		String author = jsonLinkData.getString("author");
		String id = jsonLinkData.getString("name");
		int score = jsonLinkData.getInt("score");
		String subreddit = jsonLinkData.getString("subreddit");
		Date date = convertToDate(jsonLinkData);
		boolean nsfw = jsonLinkData.getBoolean("over_18");
		Link link = new Link(title, url, permaLink, author, id, score, subreddit, date, nsfw);
		return link;
	}

	/**
	 * Convert part of a reddit link in json to a date
	 * 
	 * @param jsonLink
	 * @return
	 */
	private Date convertToDate(JSONObject jsonLink) {
		long millis = jsonLink.getLong("created_utc");
		Date date = new Date((long) 1000 * millis);
		return date;
	}

	public ArrayList<Link> getLinks() {
		return links;
	}

	/**
	 * Gets a page when there are params to be made in the request
	 * 
	 * @return String of the http request made
	 */
	private String retrievePageWithParams() {
		return http.getHttp(SimpleRedditConstants.REDDIT_URL, currentPath + SimpleRedditConstants.JSON_PATH,
				SimpleRedditConstants.PARAMS_FILE, SimpleRedditConstants.HEADERS_FILE);
	}

	/**
	 * Gets a page when there are no params to be made in the request
	 * 
	 * @return
	 */
	private String retrievePageWithoutParams() {
		return http.getHttp(SimpleRedditConstants.REDDIT_URL, currentPath + SimpleRedditConstants.JSON_PATH, null,
				SimpleRedditConstants.HEADERS_FILE);
	}

	/**
	 * Clear the parameters file and add parameters to the parameters file
	 *
	 * @param params
	 *            The parameters that will be added in the form
	 *            "paramtitle":"paraminfo" without the quotes
	 */
	private void writeToParamsFile(String... params) {
		writeStringsToFile(false, SimpleRedditConstants.PARAMS_FILE, params);
	}

	/**
	 * Add headers for the http request
	 *
	 * @param headers
	 *            The headers that will be added to the headers file in the form
	 *            "headertitle":"headerinfo" without the quotes
	 */
	private void writeToHeadersFile(String... headers) {
		writeStringsToFile(false, SimpleRedditConstants.HEADERS_FILE, headers);
	}

	/**
	 * Writes strings to a file
	 * 
	 * @param append
	 *            if the file should clear the current contents of the file or
	 *            not
	 * @param file
	 *            the file to write to
	 * @param params
	 *            parameters to write to the file
	 */
	private void writeStringsToFile(boolean append, File file, String... params) {
		BufferedWriter bw = null;
		try {
			// APPEND MODE SET HERE
			bw = new BufferedWriter(new FileWriter(file, append));
			for (String param : params) {
				bw.write(param);
				bw.newLine();
				bw.flush();
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally { // always close the file
			if (bw != null)
				try {
					bw.close();
				} catch (IOException ioe2) {
					// just ignore it
				}
		}
	}

}
