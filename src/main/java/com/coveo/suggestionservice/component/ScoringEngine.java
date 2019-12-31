/**
 * 
 */
package com.coveo.suggestionservice.component;

import com.coveo.suggestionservice.model.GeoMatch;

/**
 * 
 * Generic Scoring Engine interface
 * 
 * @author mrajasekha
 *
 */
public interface ScoringEngine
{
	/**
	 * 
	 * Method that will calculate the score using the provided params
	 * 
	 * @param geo
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public double calculateScore(GeoMatch geo, Float latitude, Float longitude);

}
