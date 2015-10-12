package com.simplereddit.controller;

import com.simplereddit.model.SimpleRedditModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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
	private Button prevBtn;

	@FXML
	void initialize() {
		webView.setCache(true);
		webView.setCacheHint(CacheHint.SPEED);
		model = new SimpleRedditModel();
		initWebView();
		webEngine.load(model.getCurrentLink().getUrl());

	}

	@FXML
	public void goToPrevLink(ActionEvent event) {
		System.out.println("Getting previous link");
		model.getPreviousLink();
		updateWebEngine();
	}

	@FXML
	public void goToNextLink(ActionEvent event) {
		System.out.println("Getting next link");
		model.getNextLink();
		updateWebEngine();
	}

	@FXML
	void getFrontPage(ActionEvent event) {
		model.retrieveFrontPage();
		updateWebEngine();
	}

	@FXML
	void getHotLinks(ActionEvent event) {
		model.getHotLinks();
		updateWebEngine();
	}

	@FXML
	void getNewLinks(ActionEvent event) {
		model.getNewLinks();
		updateWebEngine();
	}

	@FXML
	void getTopAll(ActionEvent event) {
		model.getTopAllLinks();
		updateWebEngine();
	}

	@FXML
	void getTopDay(ActionEvent event) {
		model.getTopDayLinks();
		updateWebEngine();
	}

	@FXML
	void getTopHour(ActionEvent event) {
		model.getTopHourLinks();
		updateWebEngine();
	}

	@FXML
	void getTopMonth(ActionEvent event) {
		model.getTopMonthLinks();
		updateWebEngine();
	}

	@FXML
	void getTopWeek(ActionEvent event) {
		model.getTopWeekLinks();
		updateWebEngine();
	}

	@FXML
	void getTopYear(ActionEvent event) {
		model.getTopYearLinks();
		updateWebEngine();
	}

	@FXML
	void goToSubreddit(ActionEvent event) {
		retrieveSubreddit();
	}

	@FXML
	void enterToSubreddit(ActionEvent event) {
		retrieveSubreddit();
	}

	private void retrieveSubreddit() {
		String subreddit = subredditTxtBox.getText();
		model.retrieveSubreddit(subreddit);
		updateWebEngine();
	}

	private void initWebView() {
		webEngine = webView.getEngine();
		webEngine.setJavaScriptEnabled(true);
		webEngine.locationProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				titleLabel.setText(model.getCurrentLink().getTitle());
			}
		});
		titleLabel.setText(model.getCurrentLink().getTitle());
	}

	private void updateWebEngine() {
		webEngine.load(model.getCurrentLink().getUrl());
	}

}
