package com.coveo.suggestionservice.model;

/**
 * 
 * Builder pattern for creating new GeoName objects
 * @author mrajasekha
 *
 */
public class GeoNameBuilder
{
	private Integer geoNameId;
	private String name;
	private Float latitude,longitude;
	private String country, state;

	public GeoNameBuilder(int id)
	{
		this.geoNameId = id;
	}

	public GeoNameBuilder withName(String string)
	{
		this.name = string;
		return this;
	}
	
	public GeoNameBuilder withLatitude(Float value)
	{
		this.latitude = value;
		return this;
	}
	
	public GeoNameBuilder withLongitude(Float value)
	{
		this.longitude = value;
		return this;
	}
	
	public GeoNameBuilder withCountry(String string)
	{
		this.country = string;
		return this;
	}
	
	public GeoNameBuilder withState(String string)
	{
		this.state = string;
		return this;
	}

	public GeoName build()
	{
		return new GeoName(geoNameId, name, latitude, longitude, country, state);
	}	

}
