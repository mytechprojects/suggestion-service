package com.coveo.suggestionservice.model;

import com.coveo.suggestionservice.common.SuggestionServiceConstants;

/**
 * Primary model class
 * @author mrajasekha
 *
 */
public class GeoName
{
	private Integer geoNameId;
	private String name;
	private Float latitude,longitude;
	private String country, state;
	public Integer getGeoNameId()
	{
		return geoNameId;
	}
	public void setGeoNameId(Integer geoNameId)
	{
		this.geoNameId = geoNameId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Float getLatitude()
	{
		return latitude;
	}
	public void setLatitude(Float latitude)
	{
		this.latitude = latitude;
	}
	public Float getLongitude()
	{
		return longitude;
	}
	public void setLongitude(Float longitude)
	{
		this.longitude = longitude;
	}
	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public GeoName(Integer geoNameId, String name, Float latitude, Float longitude, String country, String state)
	{
		super();
		this.geoNameId = geoNameId;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.country = country;
		this.state = state;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder geoString = new StringBuilder(name);
		geoString.append(SuggestionServiceConstants.GEONAME_SEPERATOR)
				  .append(this.state).append(SuggestionServiceConstants.GEONAME_SEPERATOR)
				  .append(this.country);
		return geoString.toString();
}


}
