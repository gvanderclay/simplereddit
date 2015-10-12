package com.simplereddit.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ModelTest {

	@Test
	public void testAtFirstLinkAfterHot() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getHotLinks();
		assertTrue(model.atFirstLink());
	}

	@Test
	public void testAtFirstLinkAfterAll() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getAllLinks();
		assertTrue(model.atFirstLink());
	}

	@Test
	public void testAtFirstLinkAfterFrontPage() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveFrontPage();
		assertTrue(model.atFirstLink());
	}

	@Test
	public void testAtFirstLinkAfterSubreddit() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("videos");
		assertTrue(model.atFirstLink());
	}

}
