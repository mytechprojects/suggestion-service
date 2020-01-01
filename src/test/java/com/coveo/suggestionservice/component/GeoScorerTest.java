package com.coveo.suggestionservice.component;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.coveo.suggestionservice.model.GeoMatch;

/**
 * 
 * Testcases to test scoring engine
 * @author mrajasekha
 *
 */
public class GeoScorerTest
{
	static ScoringEngine geoScorer;
	static GeoMatch city1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		geoScorer = new GeoScorer();
		city1 = new GeoMatch("London",39.88645f, -83.44825f);
		city1.setScore(1.0);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{

	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}
	
	@Test
	public void testScoreValueIsNonZero()
	{
		double score = geoScorer.calculateScore(city1, 10.0f, 10.0f);
		assert(score > 0);
	}
	
	@Test
	public void testNearerMatchHasGraterScore()
	{
		double score1 = geoScorer.calculateScore(city1, 39.88645f, -83.44825f);
		double score2 = geoScorer.calculateScore(city1, 10.0f, 10.0f);
		assert(score1 > score2);
	}	
	

	
}
