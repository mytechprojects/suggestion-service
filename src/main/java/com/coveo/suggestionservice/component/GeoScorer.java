package com.coveo.suggestionservice.component;

import org.springframework.stereotype.Component;

import com.coveo.suggestionservice.model.GeoMatch;
import com.coveo.suggestionservice.utils.GeoUtil;

@Component
public class GeoScorer implements ScoringEngine
{

	// Maximum distance between 2 points
	private static final double MAX_GEO_DISTANCE = 20000;
	
	// Tweak this parameter to see higher difference between scores
	private static final double WEIGHT = 10;

	public double calculateScore(GeoMatch geo, Float latitude, Float longitude)
	{
		double distance = GeoUtil.distance(geo.getLatitude(), geo.getLongitude(), latitude, longitude);
		double decreaseFactor = WEIGHT * (distance / MAX_GEO_DISTANCE);
		double score = geo.getScore() - decreaseFactor;  // Further the distance, lesser the score
		
		return score;
	}

}
