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
	public void calculateScore(GeoMatch geo, Float latitude, Float longitude);

}
