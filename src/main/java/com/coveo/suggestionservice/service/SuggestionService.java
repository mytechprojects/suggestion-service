package com.coveo.suggestionservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coveo.suggestionservice.SuggestionServiceApplication;
import com.coveo.suggestionservice.component.GeoScorer;
import com.coveo.suggestionservice.component.IndexBuilder;
import com.coveo.suggestionservice.model.GeoMatch;
import com.coveo.suggestionservice.model.GeoName;
import com.coveo.suggestionservice.model.SuggestionResponse;
import com.coveo.suggestionservice.model.UserRequest;

@Service
public class SuggestionService 
{
	public Logger logger = LoggerFactory.getLogger(SuggestionServiceApplication.class);
	
	@Autowired
	IndexBuilder geoIndexBuilder;	

	//@Override
	public SuggestionResponse getSuggestions(UserRequest userRequest)
	{
		logger.info("Service Invoked");
		String city = userRequest.getQuery();
		
		Collection<GeoName> matchingGeos = geoIndexBuilder.getGeoIndex().get(city);
		List<GeoMatch> geoMatchesList = new ArrayList();
		if(matchingGeos.isEmpty())
		{
			//No exact match.. try near match
			List<String> collect = geoIndexBuilder.getGeoIndex().keySet().parallelStream().filter(entry -> entry.startsWith(city)).collect(Collectors.toList());
			List<GeoName> nearMatchedGeoList = new ArrayList();
			collect.stream().forEach(
										partialCityName -> 
										{
											Collection<GeoName> collection = geoIndexBuilder.getGeoIndex().get(partialCityName);
											nearMatchedGeoList.addAll(collection);
										}
									);
			nearMatchedGeoList.stream().forEach(geo -> initializeResultsWithScore(geo, geoMatchesList, 0.8));
			
		}
		else
		{
			matchingGeos.stream().forEach(geo -> initializeResultsWithScore(geo, geoMatchesList, 1.0));
		}
		
		//if coordinates specified, then fine tune the score
		if(null!= userRequest.getLatitude())
		{
			GeoScorer.scoreBasedOnCoordinates(geoMatchesList, userRequest.getLatitude(), userRequest.getLongitude());
		}
		// Sort results based on score
		Collections.sort(geoMatchesList, Collections.reverseOrder());
		
		
		SuggestionResponse response = new SuggestionResponse(geoMatchesList);
		
		return response;

	}

	private void initializeResultsWithScore(GeoName geo, List<GeoMatch> geoMatchesList, double score)
	{
		
		GeoMatch aResult = new GeoMatch(geo.toString(),geo.getLatitude(), geo.getLongitude());
		aResult.setScore(score);
		geoMatchesList.add(aResult);
	}
	
}
