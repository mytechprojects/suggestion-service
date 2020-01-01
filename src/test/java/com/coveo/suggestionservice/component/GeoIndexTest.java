package com.coveo.suggestionservice.component;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.coveo.suggestionservice.common.SuggestionServiceException;
import com.coveo.suggestionservice.model.GeoName;

/**
 * 
 * Testcases to test index building scenarios
 * @author mrajasekha
 *
 */
public class GeoIndexTest
{
	static IndexBuilder builder;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		builder = new IndexBuilder();
		builder.buildGeoIndex("cities500.txt");
				
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		builder.getGeoIndex().clear();
		builder = null;
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
	public void testIndexPopulatedAsExpected1() throws SuggestionServiceException
	{
		assertNotNull(builder.getGeoIndex());
		int indexSize = builder.getGeoIndex().size();
		assert(indexSize > 0);
	}	

	@Test
	public void testIndexSupportsMultipleValuesForSameKey()
	{
		Collection<GeoName> collection = builder.getGeoIndex().get("London");
		int size = collection.size();
		assert(size > 0);
	}	
	
}
