package com.coveo.suggestionservice.model;

import java.util.List;

/**
 * Final Response class
 * 
 * @author mrajasekha
 *
 */
public class SuggestionResponse
{
	public SuggestionResponse(List<GeoMatch> suggestions)
	{
		super();
		this.suggestions = suggestions;
	}

	private List<GeoMatch> suggestions;

	public List<GeoMatch> getSuggestions()
	{
		return suggestions;
	}

	public void setSuggestions(List<GeoMatch> suggestions)
	{
		this.suggestions = suggestions;
	}

}
