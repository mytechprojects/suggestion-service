/**
 * 
 */
package com.coveo.suggestionservice.component;

import com.coveo.suggestionservice.model.GeoMatch;

/**
 * @author mrajasekha
 *
 */
public interface ScoringEngine
{
	public double calculateScore(GeoMatch geo, Float latitude, Float longitude);

}
