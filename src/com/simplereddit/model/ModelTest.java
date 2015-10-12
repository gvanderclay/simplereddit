package com.simplereddit.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.simplereddit.Link;

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

	@Test
	public void testAtFirstLinkAfterSubreddit2() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("sports");
		assertTrue(model.atFirstLink());
	}

	@Test
	public void testAtFirstLinkAfterSubreddit3() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("funny");
		assertTrue(model.atFirstLink());
	}

	@Test
	public void testAtRetreiveFromNew() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getNewLinks();
		assertTrue(model.atFirstLink());
	}

	@Test
	public void testGetPrevLinkAtFirstLink() {
		SimpleRedditModel model = new SimpleRedditModel();
		Link firstLink = model.getCurrentLink();
		for (int i = 0; i < 1000; i++) {
			assertTrue(firstLink == model.getCurrentLink());
		}
	}

	@Test
	public void testGetNextLink() {
		SimpleRedditModel model = new SimpleRedditModel();
		Link nextLink;
		for (int i = 0; i < 100; i++) {
			nextLink = model.getNextLink();
			assertTrue(nextLink == model.getCurrentLink());
		}
	}

	@Test
	public void testGetPrevLinkAfterGettingNextLink() {
		SimpleRedditModel model = new SimpleRedditModel();
		for (int i = 0; i < 100; i++) {
			model.getNextLink();
		}
		for (int i = 0; i < 100; i++) {
			Link prevLink = model.getPreviousLink();
			assertTrue(prevLink == model.getCurrentLink());
		}
	}

}
