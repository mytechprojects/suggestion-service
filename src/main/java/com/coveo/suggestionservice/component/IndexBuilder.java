package com.coveo.suggestionservice.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.coveo.suggestionservice.common.SuggestionServiceConstants;
import com.coveo.suggestionservice.common.SuggestionServiceException;
import com.coveo.suggestionservice.model.GeoName;
import com.google.common.base.Splitter;

@Component
public class IndexBuilder
{
	public Logger logger = LoggerFactory.getLogger(IndexBuilder.class);
	
	MultiValuedMap<String, GeoName> index = new ArrayListValuedHashMap<>(1000000, 5);
	
	// Can be used to support usecases like maintenance / index rebuild
	private boolean isIndexReady = false;
	
	public boolean isIndexReady()
	{
		return isIndexReady;
	}

	public void setIndexReady(boolean isIndexReady)
	{
		this.isIndexReady = isIndexReady;
	}
	
	Splitter splitter = Splitter.on(SuggestionServiceConstants.FILE_SEPERATOR);
	
	public MultiValuedMap<String, GeoName> getGeoIndex()
	{
		return index;
	}
	
	public void buildGeoIndex(String inputFile) throws SuggestionServiceException
	{
		logger.info("Starting index creation with input file --  " + inputFile);
		long start = System.currentTimeMillis();
		InputStream is = getClass().getClassLoader().getResourceAsStream(inputFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		try
		{
			while(( line = br.readLine() ) != null)
			{
				populateMap(line);
			}
		} catch (IOException e)
		{
			throw new SuggestionServiceException(SuggestionServiceConstants.IOERROR, e.getClass().getName(), e.getMessage());
		}

		logger.info("Index Build Time: " + (System.currentTimeMillis() - start) + "ms");
		logger.info("Size = " + index.size()); 
		isIndexReady = true;
		System.gc();
	}

	private void populateMap(String line)
	{
		/*
		 * Skipping builder class for memory tuning
		 * GeoName geoname = new GeoNameBuilder(Integer.parseInt(data[0]))
										.withName(data[1])
										.withLatitude(Float.parseFloat(data[4]))
										.withLongitude(Float.parseFloat(data[5]))
										.withCountry(data[8])
										.withState(data[10])
										.build(); 
		
		*/
		
		List<String> data = splitter.splitToList(line);
		GeoName geoname = new GeoName(Integer.parseInt(data.get(0)), data.get(1), Float.parseFloat(data.get(4)), Float.parseFloat(data.get(5)), data.get(8), data.get(10));
		index.put(data.get(1), geoname);
	}
}
