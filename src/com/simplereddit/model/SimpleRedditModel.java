package com.simplereddit.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	// private final int SECONDS_TO_WAIT_PER_REQUEST = 2;

	private String currentSubreddit;

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

	private boolean topHour;

	private boolean topDay;

	private boolean topWeek;

	private boolean topMonth;

	private boolean topYear;

	private boolean topAll;

	public SimpleRedditModel() {
		this.links = new ArrayList<Link>();
		this.linkCount = 0;
		setAllTopSortsToFalse();
		writeToHeadersFile("User-Agent:desktop:com.simplereddit:v0.1");
		retrieveFrontPage();
		currentSubreddit = "";
		currentLinkIndex = 0;
		currentLink = links.get(currentLinkIndex);
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

	private void resetLinks() {
		this.links.clear();
		this.linkCount = 0;

	}

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

	public boolean atFirstLink() {
		if (this.currentLinkIndex == 0 && (this.linkCount == 0 || this.linkCount == 26)) {
			return true;
		}
		return false;
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
		JSONArray JSONLinks = page.getJSONObject("data").getJSONArray("children");
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

	private String retrievePageWithParams() {
		return http.getHttp(SimpleRedditConstants.REDDIT_URL, currentPath + SimpleRedditConstants.JSON_PATH,
				SimpleRedditConstants.PARAMS_FILE, SimpleRedditConstants.HEADERS_FILE);
	}

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

	public void writeStringsToFile(boolean append, File file, String... params) {
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
	 * Wait a certain amount of seconds Used so we don't go over the maximum
	 * amount of requests per minute
	 *
	 * @param seconds
	 */
	// private void waitSeconds(int seconds) {
	// try {
	// Thread.sleep(1000 * seconds); // 1000 milliseconds is one second.
	// } catch (InterruptedException ex) {
	// Thread.currentThread().interrupt();
	// }
	// }

	public static void main(String args[]) {
		// SimpleRedditModel mod = new SimpleRedditModel();
		//
		// // mod.retrieveSubreddit("hiphopheads");
		//
		// System.out.println(mod.getCurrentLink());
		//
		// for (int i = 0; i < 26; i++) {
		// System.out.println(mod.getNextLink());
		// }
		// System.out.println("ayy");
		// for (int i = 0; i < 10; i++) {
		// System.out.println(mod.getPreviousLink());
		// }

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
