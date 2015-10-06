package com.simplereddit.controller;

import com.simplereddit.browser.MyBrowser;
import com.simplereddit.model.SimpleRedditModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button topBtn;

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
    void initialize(){
    	model = new SimpleRedditModel();
    	//browser.goToLink();;
    	initWebView();
    	webEngine.load(model.getCurrentLink().getUrl());
    }
    
    @FXML
    void goToPrevLink(ActionEvent event) {

    }
    
    private void initWebView(){
    	webEngine = webView.getEngine();
    	webEngine.locationProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				titleLabel.setText(model.getCurrentLink().getTitle());			}
		});
    	titleLabel.setText(model.getCurrentLink().getTitle());
    }
    
    

}
