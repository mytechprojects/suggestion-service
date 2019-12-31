package com.coveo.suggestionservice.component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

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

	public Logger logger = LoggerFactory.getLogger(IndexBuilder.class);
	
	Splitter splitter = Splitter.on(SuggestionServiceConstants.FILE_SEPERATOR);
	
	public MultiValuedMap<String, GeoName> getGeoIndex()
	{
		return index;
	}
	
	public void buildGeoIndex(String inputFile) throws SuggestionServiceException
	{
		logger.info("Starting index creation with input file --  " + inputFile);
		long start = System.currentTimeMillis();

		//try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(inputFile).toURI())))
		try (Stream<String> stream = Files.lines(Paths.get(inputFile)))
		{
			stream.forEach(line -> populateMap(line));

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
	
	public static void main(String args[])
	{
		try
		{
			IndexBuilder util = new IndexBuilder();
			util.buildGeoIndex("/Users/mrajasekha/tmp/allCountries.txt");
			
			MultiValuedMap<String, GeoName> geoMap = util.getGeoIndex();
			geoMap.get("London").stream().forEach(val -> System.out.println(val));

		} catch (SuggestionServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	

}
