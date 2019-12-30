package com.coveo.suggestionservice.utils;

public class GeoUtil
{
	public static double distance(double sourceLatitude, double sourceLongitude, double destLatitude, double destLongitude)
	{
		double theta = sourceLongitude - destLongitude;
		double dist = Math.sin(deg2rad(sourceLatitude)) * Math.sin(deg2rad(destLatitude)) + Math.cos(deg2rad(sourceLatitude)) * Math.cos(deg2rad(destLatitude)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);

		return (dist);
	}

	private static double deg2rad(double deg)
	{
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad)
	{
		return (rad * 180.0 / Math.PI);
	}
}
