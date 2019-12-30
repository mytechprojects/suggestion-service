package com.coveo.suggestionservice.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.coveo.suggestionservice.common.SuggestionServiceConstants;
import com.coveo.suggestionservice.common.SuggestionServiceException;

/**
 * 
 * Request context class to hold the incoming query parameters.
 * This class can be enriched to have additional attributes that can either be sent in the request or
 * derived (such as incoming request location, device type etc).
 * 
 * This can be used for future personalization or implementing rate limiting / charging
*/

public class UserRequest
{
	public String query = null;

	public Float latitude = null, longitude = null;

	public UserRequest(String query)
	{
		super();
		this.query = query;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}

	public Float getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitudeStr) throws SuggestionServiceException
	{
		try
		{
			this.latitude = Float.parseFloat(latitudeStr);
		} catch (NumberFormatException ex)
		{
			throw new SuggestionServiceException(SuggestionServiceConstants.INPUT_ERROR, ex.getClass().getName(), ex.getMessage());
		}
	}

	public Float getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitudeStr) throws SuggestionServiceException
	{
		try
		{
			this.longitude = Float.parseFloat(longitudeStr);
		} catch (NumberFormatException ex)
		{
			throw new SuggestionServiceException(SuggestionServiceConstants.INPUT_ERROR, ex.getClass().getName(), ex.getMessage());
		}
	}

	public void setGeoAttributes(HttpServletRequest httpRequest2) throws SuggestionServiceException
	{
		String latitudeStr = null, longitudeStr = null;
		if (httpRequest2.getParameterMap().containsKey(SuggestionServiceConstants.LATITUDE))
		{
			latitudeStr = httpRequest2.getParameterMap().get(SuggestionServiceConstants.LATITUDE)[0];
		}

		if (httpRequest2.getParameterMap().containsKey(SuggestionServiceConstants.LONGITUDE))
		{
			longitudeStr = httpRequest2.getParameterMap().get(SuggestionServiceConstants.LONGITUDE)[0];
		}

		if (!StringUtils.isEmpty(latitudeStr) && !StringUtils.isEmpty(longitudeStr))
		{
			setLongitude(longitudeStr);
			setLatitude(latitudeStr);

		}

	}

}
