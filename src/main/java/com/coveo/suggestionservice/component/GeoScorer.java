package com.coveo.suggestionservice.component;

import java.util.List;

import com.coveo.suggestionservice.model.GeoMatch;
import com.coveo.suggestionservice.utils.GeoUtil;

public class GeoScorer
{

	// Maximum distance between 2 points
	private static final double MAX_GEO_DISTANCE = 20000;
	
	// Tweak this parameter to see higher difference between scores
	private static final double WEIGHT = 10;

	public static void scoreBasedOnCoordinates(List<GeoMatch> geoMatchesList, Float latitude, Float longitude)
	{
		geoMatchesList.forEach(geo -> calculateScore(geo, latitude, longitude));
		
	}

	private static void calculateScore(GeoMatch geo, Float latitude, Float longitude)
	{
		double distance = GeoUtil.distance(geo.getLatitude(), geo.getLongitude(), latitude, longitude);
		double decreaseFactor = WEIGHT * (distance / MAX_GEO_DISTANCE);
		geo.setScore(geo.getScore() - decreaseFactor);  // Further the distance, lesser the score
	}

}
