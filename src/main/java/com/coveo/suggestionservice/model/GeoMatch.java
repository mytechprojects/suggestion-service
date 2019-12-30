package com.coveo.suggestionservice.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class that models a single matching Geo.  
 * In case of multiple matches, the records are sorted by score descending
 * 
 * @author mrajasekha
 *
 */
public class GeoMatch implements Comparable<GeoMatch>
{

	private String name;

	private Float latitude;

	private Float longitude;

	private double score;

	public GeoMatch(String name, Float latitude, Float longitude)
	{
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
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

	public double getScore()
	{
		return score;
	}

	public void setScore(double d)
	{
		this.score = BigDecimal.valueOf(d).setScale(3, RoundingMode.HALF_UP).doubleValue();
	}

	@Override
	public int compareTo(GeoMatch o)
	{
		if (this.score > o.score)
			return 1;
		else if (this.score < o.score)
			return -1;
		else
			return 0;
	}

}
