package com.simplereddit;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

import com.simplereddit.Link;


public class linkTest {

	String test = "Test";
	String failtest = "failure";
	String  testUrl = "www.test.com";
	String testPermaLink = "somePermaLink";
	String author = "bob";
	String id = "someId";
	int testScore = 69;
	String somesubreddit = "confusedTravolta";
	Date date = null;
	
	@Test
	public void testMakeLink(){		
		Link testLink1 = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);
		Link testLink2 = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);	
		assertTrue(testLink1.equals(testLink2));
	}
	
	@Test
	public void testgetTitle(){		
		Link testLink = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);	
		assertTrue(testLink.getTitle() == test);
		assertFalse(testLink.getTitle() == failtest);	
	}
	
	@Test
	public void testsetTitle(){		
		Link testLink = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);	
		testLink.setTitle("hello");
		assertTrue(testLink.getTitle() == "hello");
	}
	
	@Test
	public void testgetUrl(){		
		Link testLink = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);	
		assertTrue(testLink.getUrl() == testUrl);	
	}
	
	@Test
	public void testsetUrl(){		
		Link testLink = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);	
		testLink.setUrl("url");
		assertTrue(testLink.getUrl() == "url");
	}
	
	@Test
	public void testgetPermaLink(){		
		Link testLink = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);	
		assertTrue(testLink.getPermaLink() == testPermaLink);	
	}
	
	@Test
	public void testsetPermaLink(){		
		Link testLink = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);	
		testLink.setPermaLink("permalink");
		assertTrue(testLink.getPermaLink() == "permalink");
	}
	
	@Test
	public void testgetId(){		
		Link testLink = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);	
		assertTrue(testLink.getId() == id);
	}
	
	@Test
	public void testsetId(){		
		Link testLink = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);	
		testLink.setId(test);
		assertTrue(testLink.getId() == "Test");
	}
	
	@Test
	public void testgetandsetScore(){		
		Link testLink = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);	
		testLink.setScore(420);
		assertTrue(testLink.getScore() == 420);
		
	}@Test
	public void testgetandsetSubReddit(){		
		Link testLink = new Link(test,testUrl ,testPermaLink , author, id, testScore, somesubreddit, date);	
		testLink.setSubreddit("computers");
		assertTrue(testLink.getSubreddit() == "computers");
	}
}
