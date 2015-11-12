package com.simplereddit.controller;

import java.util.Date;

import com.simplereddit.Link;
import com.simplereddit.SimpleRedditConstants;
import com.simplereddit.model.SimpleRedditModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Controller for the simplereddit application
 * 
 * @author Gage and Kevin
 *
 */
public class SimpleRedditController {

	private SimpleRedditModel model;

	@FXML
	private WebView webView;
	private WebEngine webEngine;

	@FXML
	private Button nextBtn;

	@FXML
	private Button hotBtn;

	@FXML
	private Button allBtn;

	@FXML
	private Button newBtn;

	@FXML
	private MenuButton topBtn;

	@FXML
	private MenuItem topHour;

	@FXML
	private MenuItem topDay;

	@FXML
	private MenuItem topWeek;

	@FXML
	private MenuItem topMonth;

	@FXML
	private MenuItem topYear;

	@FXML
	private MenuItem topAll;

	@FXML
	private TextField subredditTxtBox;

	@FXML
	private Button goBtn;

	@FXML
	private Button frontPageBtn;

	@FXML
	private Label titleLabel;

	@FXML
	private Label infoLabel;

	@FXML
	private Button prevBtn;

	@FXML
	private Button switchButton;

	@FXML
	private MenuItem saveLinkBtn;

	@FXML
	private MenuItem viewSavedLinks;

	private boolean atPage;

	private boolean viewingSavedLinks;

	@FXML
	void initialize() {
		webView.setCache(true);
		webView.setCacheHint(CacheHint.SPEED);
		model = new SimpleRedditModel();
		initWebView();
		initButtons();
		webEngine.load(model.getCurrentLink().getUrl());
		atPage = true;
		viewingSavedLinks = false;
	}
	
	private void goToPrevLink(){
		System.out.println("Getting previous link");
		if (viewingSavedLinks) {
			model.getNextSavedLink();
		} else {
			model.getPreviousLink();
		}
		updateWebEngine();
	}
	
	@FXML
	public void goToPrevLink(ActionEvent event) {
		goToPrevLink();
	}
	
	private void goToNextLink(){
		System.out.println("Getting next link");
		if (viewingSavedLinks) {
			model.getNextSavedLink();
		} else {
			model.getNextLink();
		}
		updateWebEngine();
	}

	@FXML
	public void goToNextLink(ActionEvent event) {
		goToNextLink();
	}

	@FXML
	void getFrontPage(ActionEvent event) {
		viewingSavedLinks = false;
		model.retrieveFrontPage();
		updateWebEngine();
	}

	@FXML
	void getHotLinks(ActionEvent event) {
		viewingSavedLinks = false;
		model.getHotLinks();
		updateWebEngine();
	}

	@FXML
	void getAllLinks(ActionEvent event) {
		viewingSavedLinks = false;
		model.getAllLinks();
		updateWebEngine();
	}

	@FXML
	void getNewLinks(ActionEvent event) {
		viewingSavedLinks = false;
		model.getNewLinks();
		updateWebEngine();
	}

	@FXML
	void getTopAll(ActionEvent event) {
		viewingSavedLinks = false;
		model.getTopAllLinks();
		updateWebEngine();
	}

	@FXML
	void getTopDay(ActionEvent event) {
		viewingSavedLinks = false;
		model.getTopDayLinks();
		updateWebEngine();
	}

	@FXML
	void getTopHour(ActionEvent event) {
		viewingSavedLinks = false;
		model.getTopHourLinks();
		updateWebEngine();
	}

	@FXML
	void getTopMonth(ActionEvent event) {
		viewingSavedLinks = false;
		model.getTopMonthLinks();
		updateWebEngine();
	}

	@FXML
	void getTopWeek(ActionEvent event) {
		viewingSavedLinks = false;
		model.getTopWeekLinks();
		updateWebEngine();
	}

	@FXML
	void getTopYear(ActionEvent event) {
		viewingSavedLinks = false;
		model.getTopYearLinks();
		updateWebEngine();
	}

	@FXML
	void goToSubreddit(ActionEvent event) {
		viewingSavedLinks = false;
		retrieveSubreddit();
	}

	@FXML
	void enterToSubreddit(ActionEvent event) {
		viewingSavedLinks = false;
		retrieveSubreddit();
	}

	@FXML
	void saveLink(ActionEvent event) {
		model.saveCurrentLink();
	}

	@FXML
	void viewSavedLinks(ActionEvent event) {
		model.switchToSavedLinks();
		System.out.println(model.getCurrentLink());
		updateWebEngine();
		viewingSavedLinks = true;
	}

	/**
	 * If SimpleReddit is looking at the links then the comments button is available
	 * once the comments button is pressed the model goes to the comments of that reddit link
	 * The comments button changes to a link button, if that is pressed the link is then shown
	 * and the comment button appears once again
	 */
	
	@FXML
	void switchPage(ActionEvent event) {
		if (atPage) {
			webEngine.load("http://" + SimpleRedditConstants.REDDIT_URL + model.getCurrentLink().getPermaLink());
			switchButton.setText("Link");
		} else {
			webEngine.load(model.getCurrentLink().getUrl());
			switchButton.setText("Comments");
		}
		atPage = !atPage;
	}

	private void retrieveSubreddit() {
		String subreddit = subredditTxtBox.getText();
		model.retrieveSubreddit(subreddit);
		updateWebEngine();
	}

	/**
	 * Makes the web browser usable for the GUI. Makes the JLabel on the front
	 * of the GUI change whenever the link is changeed
	 */
	private void initWebView() {
		webEngine = webView.getEngine();
		webEngine.setJavaScriptEnabled(true);
		webEngine.locationProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Link currentLink = model.getCurrentLink();
				titleLabel.setText(currentLink.getTitle());
				infoLabel.setText(getLinkData(currentLink));

			}
		});
		titleLabel.setText(model.getCurrentLink().getTitle());
	}

	private void initButtons() {
		prevBtn.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.LEFT)) {
					goToPrevLink();
					updateWebEngine();
				}
			}

		});
		nextBtn.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.RIGHT)) {
					goToNextLink();
					updateWebEngine();
				}
			}

		});
	}

	/** 
	 * getLinkData shows all the information about the current link
	 * it is updated when user moves to the next link
	 */
	
	private String getLinkData(Link link) {
		int score = link.getScore();
		Date date = link.getDate();
		String author = link.getAuthor();
		String subreddit = link.getSubreddit();
		String dateFormatted = date.toString().substring(4);
		return "Score: " + score + "\t/r/" + subreddit + "\tAuthor: " + author + "\tDate: " + dateFormatted;
	}

	/**
	 * Change the web browser
	 */
	private void updateWebEngine() {
		webEngine.load(model.getCurrentLink().getUrl());
	}
}
