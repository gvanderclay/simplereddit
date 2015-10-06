package com.simplereddit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.simplereddit.model.HTTPRequester;

public class Test {

	public static void main(String args[]) {
		HTTPRequester http = new HTTPRequester();
		String frontPage = http.getHttp(SimpleRedditConstants.REDDIT_URL, "/.json", null, null);
		JSONObject json = new JSONObject(frontPage);
		JSONArray links = json.getJSONObject("data").getJSONArray("children");
		for (int i = 0; i < links.length(); i++) {
			JSONObject link = links.getJSONObject(i).getJSONObject("data");
			String author = link.getString("author");
			int score = link.getInt("score");
			String permaLink = link.getString("permalink");
			String url = link.getString("url");
			String title = link.getString("title");
			System.out.printf("Author: %s\tScore: %s\tPermalink: %s\tURL: %s\tTitle: %s\t\n", author, score, permaLink,
					url, title);
		}
	}

}
