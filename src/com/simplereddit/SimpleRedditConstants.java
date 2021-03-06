package com.simplereddit;

import java.io.File;

/**
 *
 * Constants that will be used throughout the code
 * @author Gage and Kevin
 *
 */
public final class SimpleRedditConstants {

	/**
	 * URL for Reddit
	 */
	public static final String REDDIT_URL = "www.reddit.com";

	/**
	 * path to get the json representation of a subreddit
	 */
	public static final String JSON_PATH = "/.json";
	
	/**
	 * File that contains the params for the http request to reddit
	 */
	public static final File PARAMS_FILE = new File("resources/http/params");

	/**
	 * File that contains the headers for the http request to reddit
	 */
	public static final File HEADERS_FILE = new File("resources/http/headers");

	
	/**
	 * File used as a placeholder for the entity file 
	 */
	public static final File EMPTY_FILE = new File("resources/http/placeholder");
	

	/**
	 * The caller should be prevented from constructing objects of this class,
	 * by declaring this private constructor.
	 */
	private SimpleRedditConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
