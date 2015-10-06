package com.simplereddit.browser;

import java.net.MalformedURLException;
import java.net.URL;

import com.simplereddit.Link;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class MyBrowser extends Region {
	HBox toolbar;

	WebView webView = new WebView();
	WebEngine webEngine = webView.getEngine();

	public MyBrowser() {

		URL reddit = null;
		try {
			reddit = new URL("http://reddit.com");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webEngine.load(reddit.toExternalForm());

		getChildren().add(webView);

	}

	
}