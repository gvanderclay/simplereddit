package com.simplereddit.controller;

import com.simplereddit.browser.MyBrowser;
import com.simplereddit.model.SimpleRedditModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SimpleRedditController {

	private SimpleRedditModel model;
	
	@FXML
	private MyBrowser browser;
	
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
    	
    	
    }
    
    @FXML
    void goToPrevLink(ActionEvent event) {

    }
    
    

}
