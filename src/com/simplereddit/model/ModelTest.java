package com.simplereddit.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.simplereddit.Link;

/**
 * Class that tests the simpleredditmodel class
 * @author Gage and Kevin
 *
 */
public class ModelTest {

	@Test
	public void testAtFirstLinkAfterHot() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getHotLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testGetHotOfSubreddit(){
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("videos");
		model.getHotLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testAtFirstLinkAfterAll() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getAllLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testAtFirstLinkAfterFrontPage() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveFrontPage();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testAtFirstLinkAfterSubreddit() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("videos");
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testAtFirstLinkAfterSubreddit2() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("sports");
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testAtFirstLinkAfterSubreddit3() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("funny");
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testAtRetreiveFromNew() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getNewLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testNewFromSubreddit(){
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("videos");
		model.getNewLinks();
		assertTrue(model.atFirstLink());
	}

	@Test
	public void testGetPrevLinkAtFirstLink() {
		SimpleRedditModel model = new SimpleRedditModel();
		Link firstLink = model.getCurrentLink();
		for (int i = 0; i < 1000; i++) {
			assertTrue(firstLink == model.getPreviousLink());
		}
		waitOneSecond();
	}

	@Test
	public void testGetNextLink() {
		SimpleRedditModel model = new SimpleRedditModel();
		Link nextLink;
		for (int i = 0; i < 100; i++) {
			nextLink = model.getNextLink();
			assertTrue(nextLink == model.getCurrentLink());
		}
		waitOneSecond();
	}

	@Test
	public void testGetPrevLinkAfterGettingNextLink() {
		SimpleRedditModel model = new SimpleRedditModel();
		for (int i = 0; i < 50; i++) {
			model.getNextLink();
			if(i % 2 == 0){
				waitOneSecond();
			}
		}
		for (int i = 0; i < 50; i++) {
			Link prevLink = model.getPreviousLink();
			assertTrue(prevLink == model.getCurrentLink());
			if(i % 2 == 0){
				waitOneSecond();
			}
		}
		waitOneSecond();
	}

	@Test
	public void testGetNextLinkAfterGettingNextLinksAndPrevLinks() {
		SimpleRedditModel model = new SimpleRedditModel();
		for (int i = 0; i < 100; i++) {
			model.getNextLink();
		}
		waitOneSecond();
		for (int i = 0; i < 50; i++) {
			model.getPreviousLink();
		}
		waitOneSecond();
		for (int i = 0; i < 50; i++) {
			Link nextLink = model.getNextLink();
			System.out.println(nextLink.getTitle());
			assertTrue(nextLink == model.getCurrentLink());
		}
		waitOneSecond();
	}

	@Test
	public void testGetTopHourLinks() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getTopHourLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testGetTopDayLinks() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getTopDayLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testGetTopWeekLinks() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getTopWeekLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testGetTopMonthLinks() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getTopMonthLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testGetTopYearLinks() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getTopYearLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testGetTopAllLinks() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.getTopAllLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testGetTopHourLinksSubreddit() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("sports");
		model.getTopHourLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testGetTopDayLinksSubreddit() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("sports");
		model.getTopDayLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testGetTopWeekLinksSubreddit() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("videos");
		model.getTopWeekLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testGetTopMonthLinksSubreddit() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("funny");
		model.getTopMonthLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testGetTopYearLinksSubreddit() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("sports");
		model.getTopYearLinks();
		assertTrue(model.atFirstLink());
	}

	@Test
	public void testSubredditWithLowAmountOfLinks() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("ooer");
		model.getTopHourLinks();
		for (int i = 0; i < 100; i++) {
			model.getNextLink();
		}
		waitOneSecond();
	}

	@Test
	public void testGetTopAllLinksSubreddit() {
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveSubreddit("sports");
		model.getTopAllLinks();
		assertTrue(model.atFirstLink());
		waitOneSecond();
	}

	@Test
	public void testSavingLinks(){
		SimpleRedditModel model = new SimpleRedditModel();
		model.retrieveFrontPage();
		for(int i = 0; i < 25; i++){
			model.saveCurrentLink();
			model.getNextLink();
		}
		model.switchToSavedLinks();
		for(int i = 0; i < 25; i++){
			assertTrue(model.getCurrentLink().equals(model.getSavedLinks().get(i)));
			model.getNextSavedLink();
		}
		for(int i = 24; i >= 0; i--){
			assertTrue(model.getCurrentLink().equals(model.getSavedLinks().get(i)));
			model.getPrevSavedLink();
		}
	}

	private void waitOneSecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
}
